package habsida.spring.boot_security.demo.controllers;

import habsida.spring.boot_security.demo.models.User;
import habsida.spring.boot_security.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class HelloController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @GetMapping(value="/login")
    public String showLoginPage(ModelMap model){
        //using this code just for adding some accounts in users table
        List<User> lu = userService.listUsers();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "login";
        }
        return "redirect:/";
    }

    @GetMapping(value = "/")
    public String printWelcome(ModelMap model, Authentication auth){
        Set<String> roles = AuthorityUtils.authorityListToSet(auth.getAuthorities());
        model.addAttribute("authName", auth.getName());
        model.addAttribute("authRole", AuthorityUtils.authorityListToSet(auth.getAuthorities()));
        List<String> messages = new ArrayList<>();

        messages.add("Welcome to my first project!");
        messages.add("I have just launched my first web application!");
        model.addAttribute("messages", messages);
        model.addAttribute("HelloTitle", "Task_3_1_3");
        model.addAttribute("userControllerPage", "user");
        model.addAttribute("adminControllerPage", "admin");
        return "index";

    }


}
