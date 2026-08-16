package com.training.training.controller;

import com.training.training.dto.UserRequest;
import com.training.training.dto.UserResponse;
import com.training.training.entity.User;
import com.training.training.mapper.UserMapper;
import com.training.training.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.training.training.dto.UserWithAddressesResponse;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }


    @PostMapping
    public UserResponse addUser(@Valid @RequestBody UserRequest request) {

        User user = userMapper.toEntity(request);
        User savedUser = userService.addUser(user);

        return userMapper.toResponse(savedUser);
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequest request) {

        User updatedUser = userMapper.toEntity(request);
        User savedUser = userService.updateUser(id, updatedUser);

        return userMapper.toResponse(savedUser);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }


    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id) {
        User user = userService.getUser(id);
        return userMapper.toResponse(user);
    }
    @GetMapping("/with-addresses")
    public List<UserWithAddressesResponse> getAllUsersWithAddresses() {

        List<User> users = userService.getAllActiveUsersWithAddresses();

        return users.stream()
                .map(user -> userMapper.toWithAddressesResponse(user))
                .toList();
    }


    @GetMapping("/by-city")
    public List<UserWithAddressesResponse> getUsersByCity(@RequestParam String city) {

        return userService.getUsersByCityWithAddresses(city)
                .stream()
                .map(user -> userMapper.toWithAddressesResponse(user))
                .toList();
    }

    @GetMapping("/by-phone-prefix")
    public List<UserResponse> getUsersByPhonePrefix(
            @RequestParam String prefix) {

        return userService.getActiveUsersByPhonePrefix(prefix)
                .stream()
                .map(user -> userMapper.toResponse(user))
                .toList();
    }


    @GetMapping("/search")
    public List<UserResponse> searchUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phonePrefix) {

        return userService.searchActiveUsers(name, phonePrefix)
                .stream()
                .map(user -> userMapper.toResponse(user))
                .toList();
    }




}