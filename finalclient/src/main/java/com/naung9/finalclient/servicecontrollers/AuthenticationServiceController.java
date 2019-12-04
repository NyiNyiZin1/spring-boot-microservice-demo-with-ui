package com.naung9.finalclient.servicecontrollers;

import com.naung9.finalclient.models.Role;
import com.naung9.finalclient.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@FeignClient("eureka-client1") // Application Name
public interface AuthenticationServiceController {
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    User login(@RequestParam("email")String email, @RequestParam("password")String password);

    @PostMapping(value = "/saveUser")
    User saveUser(User user);

    @GetMapping("/user/{userId}")
    User getUser(@PathVariable int userId);

    @GetMapping(value = "/user/delete/{userId}")
    String deleteUser(@PathVariable int userId);

    @GetMapping("/role/all")
    List<Role> getAllRoles();

    @GetMapping("/user/all")
    List<User> getAllUsers();
}
