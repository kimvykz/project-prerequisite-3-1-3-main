package habsida.spring.boot_security.demo.controllers;

import habsida.spring.boot_security.demo.models.User;
import habsida.spring.boot_security.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
public class UserController {

    private User userForMod;
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @GetMapping(value="/user")
    public String getUserForm(Model model, Authentication auth){
        Set<String> roles = AuthorityUtils.authorityListToSet(auth.getAuthorities());
        model.addAttribute("authName", auth.getName());
        model.addAttribute("authRole", AuthorityUtils.authorityListToSet(auth.getAuthorities()));

        model.addAttribute("userControllerPage", "user");
        model.addAttribute("adminControllerPage", "admin");

        List<User> listUsers = userService.listUsers();
        model.addAttribute("UserTitle", "User page");
        model.addAttribute("ListOfUsers", listUsers);
        return "user";
    }

    @GetMapping(value="/admin")
    public String getAdminForm(Map<String, Object> model, Authentication auth) {
        Set<String> roles = AuthorityUtils.authorityListToSet(auth.getAuthorities());
        model.put("authName", auth.getName());
        model.put("authRole", AuthorityUtils.authorityListToSet(auth.getAuthorities()));

        model.put("userControllerPage", "user");
        model.put("adminControllerPage", "admin");

        List<User> listUsers = userService.listUsers();
        model.put("UserTitle", "Admin Controller page");
        model.put("ListOfUsers", listUsers);
        return "admin";
    }

    @GetMapping(value="/user_create")
    public String createUser(Map<String, Object> model, Authentication auth){
        Set<String> roles = AuthorityUtils.authorityListToSet(auth.getAuthorities());
        model.put("authName", auth.getName());
        model.put("authRole", AuthorityUtils.authorityListToSet(auth.getAuthorities()));

        model.put("userControllerPage", "user");
        model.put("adminControllerPage", "admin");
        User newUser = new User();
        model.put("newuser", newUser);
        return"user_create";
    }

    @RequestMapping(value="/save", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute("newuser") User newuser){
        userService.add(newuser);
        return "redirect:/admin";
    }

    @GetMapping(value="/modify")
    public String modifyUser(@RequestParam Long id, Map<String, Object> model){
        User userForMod = userService.findUserById(id);
        model.put("userForMod", userForMod);
        return "redirect:/admin";
    }

    @PostMapping(value="/update/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute("user") User user){
        userService.modify(user);
        return "redirect:/admin";
    }

    @PostMapping(value="/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        User userForDel = userService.findUserById(id);
        userService.remove(userForDel);
        return "redirect:/admin";
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }
}
