package by.itstep.myFirstSpringBootApp.controller;

import by.itstep.myFirstSpringBootApp.domain.Animal;
import by.itstep.myFirstSpringBootApp.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/animals")
public class AnimalController {

    AnimalService animalService;

    @Autowired
    public void setAnimalService(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping()
    public String getAnimals(
            @RequestParam(name = "nameFilter", required = false, defaultValue = "") String nameFilter,
            @RequestParam(name = "speciesFilter", required = false, defaultValue = "") String speciesFilter,
            @RequestParam(name = "editAnimal", required = false, defaultValue = "") Animal animal,
            @RequestParam(name = "removeAnimal", required = false, defaultValue = "") Animal removeAnimal,
            @RequestParam(name = "repairAnimal", required = false, defaultValue = "") Animal repairAnimal,
            @PageableDefault(sort = {"name", "sex"}, direction = Sort.Direction.ASC) Pageable pageable,
            Model model
    ){

        System.out.println(nameFilter);
        System.out.println(speciesFilter);
//        model.addAttribute("animals", animalService.findAllAnimals());
        Page<Animal> page = animalService.findAllAnimals(nameFilter, speciesFilter, pageable);
        model.addAttribute("page", page);
        model.addAttribute("url", "/animals");
        model.addAttribute("nameFilter", nameFilter);
        model.addAttribute("speciesFilter", speciesFilter);
        model.addAttribute("hasContent", page.hasContent());

        if (animal != null){
            model.addAttribute("animal", animal);
        }


        if (removeAnimal != null){
            animalService.removeAnimal(removeAnimal);

            return "redirect:animals";
        }

        if (repairAnimal != null){
            animalService.repairAnimal(repairAnimal);

            return "redirect:animals";
        }

        return "animals";
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping()
    public String addAnimal(
            @Valid Animal animal,
            BindingResult bindingResult,
            Model model,
            @PageableDefault(sort = {"name", "sex"}, direction = Sort.Direction.ASC) Pageable pageable
            ){
        if (bindingResult.hasErrors()){
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("animal", animal);
            Page<Animal> page = animalService.findAllAnimals(pageable);
            model.addAttribute("page", page);
            model.addAttribute("url", "/animals");
//            model.addAttribute("animals", animalService.findAllAnimals());

            return "animals";
        } else {
            animalService.addAnimal(animal);
            return "redirect:/animals";
        }
    }
}
