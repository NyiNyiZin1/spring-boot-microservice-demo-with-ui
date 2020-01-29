package com.nnz.finalclient.controllers;

import com.nnz.finalclient.models.Role;
import com.nnz.finalclient.models.User;
import com.nnz.finalclient.servicecontrollers.AuthenticationServiceController;

import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collection;
import java.util.List;

@Controller
public class MainController {
    final
    AuthenticationServiceController authenticationServiceController;


    public MainController(AuthenticationServiceController authenticationServiceController) {
        this.authenticationServiceController = authenticationServiceController;
    }

    @GetMapping("/register")
    public String registerPage(Model model){
        Collection<Role> roles = authenticationServiceController.getAllRoles();
        model.addAttribute("roles", roles);
        model.addAttribute("user", new User());
        return "register";
    }

    @GetMapping("/index")
    public String index(Model model){
        model.addAttribute("users", authenticationServiceController.getAllUsers());
        return "index";
    }

    @GetMapping("/user/{userId}")
    public String getUserById(@PathVariable int userId, Model model){
        model.addAttribute("user", authenticationServiceController.getUser(userId));
        model.addAttribute("roles", authenticationServiceController.getAllRoles());
        return "user";
    }

    @GetMapping("/login")
    public String loginPage(Authentication authentication){
        if(authentication !=null && authentication.isAuthenticated()){
            return "redirect:/index";
        }
        return "login";
    }

    @PostMapping("/register")
    public String saveUser(User user){
        System.out.println("User Email : "+user.getEmail()+" Roles : "+ user.getRoles()+", Password : "+user.getPassword());
        authenticationServiceController.saveUser(user);
        return "redirect:/login";
    }

    // Convert Field "roles" coming from client to Role Object Collection/List
    @InitBinder
    public void objConverter(WebDataBinder dataBinder) throws Exception{
        dataBinder.registerCustomEditor(List.class, "roles", new CustomCollectionEditor(List.class){
            @Override
            protected Object convertElement(Object element) {
                if(element instanceof Role){
                    return element;
                }else if(element instanceof String){
                    Role role = new Role();
                    role.setId(Integer.parseInt(element.toString()));
                    return role;
                }
                return null;
            }
        });
    }
}
