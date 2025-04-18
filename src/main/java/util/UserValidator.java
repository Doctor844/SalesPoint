package util;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import service.UserService;


@Component
public class UserValidator implements Validator {


    private final UserService userService;


    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        try {

        userService.loadUserByUsername(user.getUsername());
        }catch (UsernameNotFoundException e){
            return;
        }
        errors.rejectValue("username", "", "Пользователь с таким именем существует");
    }
}
