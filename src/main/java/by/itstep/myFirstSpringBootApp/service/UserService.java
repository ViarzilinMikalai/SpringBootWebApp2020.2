package by.itstep.myFirstSpringBootApp.service;

import by.itstep.myFirstSpringBootApp.domain.Role;
import by.itstep.myFirstSpringBootApp.domain.User;
import by.itstep.myFirstSpringBootApp.repository.UserRepo;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    public final UserRepo userRepo;
    public final MailSenderService mailSenderService;


    public void addUser(User user) {

        user.setRoles(Collections.singleton(Role.USER));
        user.setActive(false);
        user.setActivationCode(UUID.randomUUID().toString());

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
                            "Welcome to animali clinic. Please visit next link http://localhost:8080/activate/%s",
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


    public void updateProfile(User user, String password, String email){

        String userEmail = user.getEmail();

        boolean isChanged = (email != null && email.equals(userEmail) || userEmail != null && userEmail.equals(email));

        if (isChanged){
            user.setEmail(email);

            if(!StringUtils.isEmpty(email)){
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }

        if(!StringUtils.isEmpty(password)){
            user.setPassword(password);
        }

        userRepo.save(user);
        if(isChanged){
            sendMessage(user);
        }
    }
}
