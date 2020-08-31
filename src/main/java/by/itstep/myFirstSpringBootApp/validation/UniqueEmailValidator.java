package by.itstep.myFirstSpringBootApp.validation;

import by.itstep.myFirstSpringBootApp.domain.User;
import by.itstep.myFirstSpringBootApp.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, User> {

    public final UserRepo userRepo;

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {

        if (validEmail(user)){
            return true;
        } else {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    constraintValidatorContext.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("email").addConstraintViolation();
            return false;
        }
    }

    boolean validEmail(User user){
        if (user.getId() == null) {
            return userRepo.findByEmail(user.getEmail()) == null;
        } else {
            return user.getId() == userRepo.findByEmail(user.getEmail()).getId();
        }
    }

}
