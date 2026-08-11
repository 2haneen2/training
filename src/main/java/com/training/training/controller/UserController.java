package com.training.training.controller;

import com.training.training.dto.UserRequest;
import com.training.training.dto.UserResponse;
import com.training.training.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public UserResponse addUser(@Valid @RequestBody UserRequest request) {
        return userService.addUser(request);
    }

    @PutMapping("/{id}")  //-->  after "/users"
    public UserResponse updateUser(@PathVariable Long  id ,@Valid @RequestBody  UserRequest request){
        return userService.updateUser(id,request);
    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
    @GetMapping("/{id}/exists")
    public boolean existsUser(@PathVariable Long id) {
        return userService.existsUser(id);
    }


}