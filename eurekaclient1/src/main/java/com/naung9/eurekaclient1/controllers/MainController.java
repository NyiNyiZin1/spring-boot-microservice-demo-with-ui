package com.naung9.eurekaclient1.controllers;

import com.naung9.eurekaclient1.entities.Role;
import com.naung9.eurekaclient1.entities.User;
import com.naung9.eurekaclient1.repositories.RoleRepo;
import com.naung9.eurekaclient1.repositories.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
public class MainController {

    final UserRepo userRepo;
    final PasswordEncoder passwordEncoder;
    final
    RoleRepo roleRepo;


    public MainController(UserRepo userRepo, PasswordEncoder passwordEncoder, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.roleRepo = roleRepo;
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public User login(@RequestParam("email")String email, @RequestParam("password")String password){
        User user = userRepo.findByEmail(email).orElseThrow(
                ()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return user;
    }

    @PostMapping("/saveUser")
    public User saveUser(@RequestBody User user){
        System.out.println(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @GetMapping("/user/{userId}")
    public User getUser(@PathVariable int userId){
        return userRepo.findById(userId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/user/all")
    public List<User> getAllUsers(){
        return userRepo.findAll();
    }

    @GetMapping("/role/all")
    public List<Role> getAllRoles(){
        return roleRepo.findAll();
    }

    @GetMapping(value = "/user/delete/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteUser(@PathVariable int userId){
        userRepo.deleteById(userId);
        return "{\"operation\":\"success\"}";
    }


}
