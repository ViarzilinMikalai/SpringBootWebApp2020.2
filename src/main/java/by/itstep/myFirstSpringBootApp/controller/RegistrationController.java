package by.itstep.myFirstSpringBootApp.controller;

import by.itstep.myFirstSpringBootApp.domain.User;
import by.itstep.myFirstSpringBootApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/registration")
public class RegistrationController {

    public final UserService userService;

    @GetMapping
    public String form(
            @RequestParam(name = "editUser", required = false, defaultValue = "") User user,
            Model model
    ){
        model.addAttribute("user", user);
        model.addAttribute("users", userService.users());

        return "registration";
    }


    @PostMapping
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
}
