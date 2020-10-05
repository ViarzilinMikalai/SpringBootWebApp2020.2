package by.itstep.myFirstSpringBootApp.controller;

import by.itstep.myFirstSpringBootApp.domain.Animal;
import by.itstep.myFirstSpringBootApp.domain.User;
import by.itstep.myFirstSpringBootApp.service.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/animals")
@RequiredArgsConstructor
public class AnimalController {

    public final AnimalService animalService;

    @GetMapping
    public String getAnimals(
            @RequestParam(required = false) String nameFilter,
            @RequestParam(required = false) String speciesFilter,
            @RequestParam(required = false, name = "editAnimal") Animal animal,
            @RequestParam(name = "removeId", required = false) Animal removeAnimal,
            @RequestParam(name = "repairId", required = false) Animal repairAnimal,
            @PageableDefault(sort = {"id", "name"}, direction = Sort.Direction.ASC) Pageable pageable,
            Model model
    ) {
//    model.addAttribute("animals", animalService.findAllAnimal());
        Page<Animal> page = animalService.findAllAnimals(nameFilter, speciesFilter, pageable);
        model.addAttribute("page", page);

        model.addAttribute("hasContent", page.hasContent());

        model.addAttribute("url", "/animals");
        model.addAttribute("nameFilter", nameFilter);
        model.addAttribute("speciesFilter", speciesFilter);
        if (animal != null) {
            model.addAttribute("animal", animal);
        }
        if (removeAnimal != null) {
            animalService.removeAnimal(removeAnimal);
            return "redirect:/animals";
        }

        if (repairAnimal != null) {
            animalService.repairAnimal(repairAnimal);
            return "redirect:/animals";
        }

        return "animals";
    }

    @GetMapping("/addanimal/{animalOwner}")
    public String addOrEditAnimal(
            Model model,
            @PathVariable User animalOwner
    ) {
        model.addAttribute("animals", animalService.findAllAnimalsByOwner(animalOwner));
        model.addAttribute("user", animalOwner);
        return "addanimal";
    }

    //  @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addanimal/{id}")
    public String addAnimal(
//      @PageableDefault(sort = {"name", "species", "sex"}, direction = Sort.Direction.ASC) Pageable pageable,
            @PathVariable("id") User animalOwner,
            @Valid Animal animal,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("user", animalOwner);
            model.addAttribute("animal", animal);
            model.addAttribute("animals", animalService.findAllAnimalsByOwner(animalOwner));

//      model.addAttribute("animals", animalService.findAllAnimal());
//      Page<Animal> page = animalService.findAllAnimals(pageable);
//      model.addAttribute("page", page);
//      model.addAttribute("url", "/animals");

            return "addanimal";
        } else {
            animalService.addAnimal(animal);
            return "redirect:/animals/addanimal/{id}";
        }
    }
}