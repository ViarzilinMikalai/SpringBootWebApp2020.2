package by.itstep.myFirstSpringBootApp.controller;

import by.itstep.myFirstSpringBootApp.domain.User;
import by.itstep.myFirstSpringBootApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class RegistrationController {

    public final UserService userService;

    @GetMapping("/registration")
    public String form(
            @RequestParam(name = "editUser", required = false, defaultValue = "") User user,
            Model model
    ){
        model.addAttribute("user", user);
        model.addAttribute("users", userService.users());

        return "registration";
    }


    @PostMapping("/registration")
    public String addUser(
            @Valid User user,
            BindingResult bindingResult,
            Model model
            ){

        if (bindingResult.hasErrors()){
            model.addAttribute("users", userService.users());
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            return "registration";

        } else {
            userService.addUser(user);
            return "redirect:/animals";
        }
    }



    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code){

        boolean isActivated = userService.activateUser(code);

        if(isActivated) {
            model.addAttribute("message", "success");
        } else {
            model.addAttribute("message", "Activation code isn't found");
        }

        return "activation";
    }
}
