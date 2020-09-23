package by.itstep.myFirstSpringBootApp.validation;

import by.itstep.myFirstSpringBootApp.domain.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordConfirmValidator implements ConstraintValidator<PasswordConfirm, User> {
    @Override
    public void initialize(PasswordConfirm constraintAnnotation) {
    }

    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {

        if (!user.getPasswordConfirm().isEmpty() && user.getPassword().equals(user.getPasswordConfirm())){
            return true;
        } else {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    constraintValidatorContext.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("passwordConfirm").addConstraintViolation();
            return false;
        }
    }
}
