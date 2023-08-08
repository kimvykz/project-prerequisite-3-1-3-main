package habsida.spring.boot_security.demo.controllers;

import habsida.spring.boot_security.demo.models.User;
import habsida.spring.boot_security.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HelloController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @GetMapping(value = "/")
    public String printWelcome(ModelMap model){

        List<User> lu = userService.listUsers();

        List<String> messages = new ArrayList<>();
        messages.add("Welcome to my first project!");
        messages.add("I have just launched my first web application!");
        model.addAttribute("messages", messages);
        model.addAttribute("HelloTitle", "Task_2_2_3");
        model.addAttribute("userControllerPage", "user");
        model.addAttribute("adminControllerPage", "admin");
        return "index";

    }
}
