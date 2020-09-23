package by.itstep.myFirstSpringBootApp.controller;

import by.itstep.myFirstSpringBootApp.domain.User;
import by.itstep.myFirstSpringBootApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
@RequestMapping("/user")
public class UserController {

    public final UserService userService;

    @GetMapping
    public String userData(Model model, @AuthenticationPrincipal User user){

        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/password")
    public String userPassword(Model model, @AuthenticationPrincipal User user){

        return "password";
    }

    @PostMapping("/password")
    public String editUser(
            @RequestParam(name = "oldPassword", defaultValue = "", required = false) String oldPassword,
            @RequestParam(name = "newPassword", defaultValue = "", required = false) String newPassword,
            @RequestParam(name = "confirmPassword", defaultValue = "", required = false) String confirmPassword,
            Model model,
            @AuthenticationPrincipal User user){

        Map<String, String> errorMap = userService.changePassword(oldPassword, newPassword, confirmPassword, user);
        if (errorMap.isEmpty()){
            return "redirect:/logout";
        }
        model.mergeAttributes(errorMap);

        return "password";

    }
    
    
    @GetMapping("/email")
    public String userEmail(){

        return "email";
    }


    @PostMapping("/email")
    public String changeEmail(
            @RequestParam(name = "oldEmail", defaultValue = "") String oldEmail,
            @RequestParam(name = "newEmail", defaultValue = "") String newEmail,
            @AuthenticationPrincipal User user,
            Model model
    ){
        Map<String, String> emailErrors = userService.changeEmail(oldEmail, newEmail, user);

        if (emailErrors.isEmpty()){
            return "redirect:/logout";
        } else {
            model.mergeAttributes(emailErrors);
            model.addAttribute("oldEmail", oldEmail);
            model.addAttribute("newEmail", newEmail);

            return "email";
        }
    }

}
