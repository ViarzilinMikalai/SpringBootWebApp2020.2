package by.itstep.myFirstSpringBootApp.service;

import by.itstep.myFirstSpringBootApp.domain.Role;
import by.itstep.myFirstSpringBootApp.domain.User;
import by.itstep.myFirstSpringBootApp.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    public final PasswordEncoder passwordEncoder;
    public final UserRepo userRepo;
    public final MailSenderService mailSenderService;


    public void addUser(User user) {

        user.setRoles(Collections.singleton(Role.USER));
        user.setActive(false);
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepo.save(user);
        sendMessage(user);
    }


    public List<User> users(){
        return userRepo.findAll(Sort.by("username"));
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }


    private void sendMessage(User user){
        if(!StringUtils.isEmpty(user.getEmail())){

            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to animal clinic. Please visit next link http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );

            mailSenderService.send(user.getEmail(), "Activation code", message);
        }
    }


    public boolean activateUser(String code){
        User userByCode = userRepo.findByActivationCode(code);

        if (userByCode == null){
            return false;
        } else {

            userByCode.setActivationCode(null);
            userByCode.setActive(true);
            userRepo.save(userByCode);

            return true;
        }
    }


    public void saveUser(User user, String username, Map<String, String> form){

        user.setUsername(username);

        Set<String> roles = Arrays.stream(Role.values())
            .map(Role::name)
            .collect(Collectors.toSet());

        user.getRoles().clear();

        for(String key : form.keySet()){
            if(roles.contains(key)){
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepo.save(user);
    }

    public Map<String, String> changePassword(String oldPassword, String newPassword, String confirmPassword, User user) {
        Map<String, String> errorMap = new HashMap<>();
        if(!passwordEncoder.matches(oldPassword, user.getPassword())){
            errorMap.put("oldPasswordError", "Password incorrect");
        }
        if(newPassword.isEmpty()){
            errorMap.put("newPasswordError", "New password must contain some");
        }
        if(!newPassword.equals(confirmPassword)){
            errorMap.put("confirmPasswordError", "Password not similar");
        }

        if (errorMap.isEmpty()){
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepo.save(user);
        }

        return errorMap;
    }

    public Map<String, String> changeEmail(String oldEmail, String newEmail, User user) {
        Map<String, String> emailError = new HashMap<>();

        if(oldEmail.isEmpty()){
            emailError.put("oldEmailEmpty", "Email must not be empty");
        }
        if (!oldEmail.equals(user.getEmail())){
            emailError.put("oldEmailError", "Email not similar");
        }

        if (!newEmail.matches("[A-z0-9_-]+@[A-z0-9_-]+\\.[A-z0-9_-]+")){
            emailError.put("newEmailError", "EMail is not correct");
        }

        if (userRepo.findByEmail(newEmail) != null){
            emailError.put("notUniqueError", "Email isn't unique");
        }

        if(emailError.isEmpty()){
            user.setEmail(newEmail);
            user.setActivationCode(UUID.randomUUID().toString());
            user.setActive(false);

            userRepo.save(user);
            sendMessage(user);
        }

        return emailError;
    }


    public User findById(Long id) {
        Optional<User> userOptional = userRepo.findById(id);
        return userOptional.get();
    }
}
