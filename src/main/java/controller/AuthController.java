package controller;



import jakarta.servlet.http.HttpServletRequest;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import service.UserRegistration;
import util.UserValidator;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserValidator userValidator;
    private final UserRegistration userRegistration;

    @Autowired
    public AuthController(UserValidator userValidator, UserRegistration userRegistration) {
        this.userValidator = userValidator;
        this.userRegistration = userRegistration;
    }

    @GetMapping("/login")
    public String loginPage(HttpServletRequest request, Model model) {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");
        if (csrfToken != null) {
            model.addAttribute("_csrf", csrfToken);
        }
        return "auth/login";
    }


    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("user") User user) {
        return "auth/registration";
    }

    @RequestMapping(value ="/registration", method = RequestMethod.POST)
    public String performRegistration(@ModelAttribute("user") User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        userRegistration.register(user);

        if (bindingResult.hasErrors()) {
            return "/auth/registration";
        }
        return "redirect:/auth/login";
    }
}
