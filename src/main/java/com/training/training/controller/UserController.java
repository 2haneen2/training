package com.training.training.controller;

import com.training.training.dto.UpdateUserRequest;
import com.training.training.dto.UserDto;
import com.training.training.entity.User;
import com.training.training.mapper.UserMapper;
import com.training.training.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.training.training.dto.UserWithDetailsDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    public UserDto createUser(@Valid @RequestBody UpdateUserRequest request) {

        User user = userMapper.mapToUser(request);
        User createdUser = userService.createUser(user);

        return userMapper.mapToUserDto(createdUser);
    }

    @PutMapping("/{id}")
    public UserDto updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request) {

        User updatedUser = userMapper.mapToUser(request);
        User savedUser = userService.updateUser(id, updatedUser);

        return userMapper.mapToUserDto(savedUser);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }


    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id) {
        User user = userService.getUser(id);
        return userMapper.mapToUserDto(user);
    }

    @GetMapping("/details")
    public List<UserWithDetailsDto> getAllUsersWithAddresses() {

        List<User> users = userService.getAllActiveUsersWithAddresses();

        return users.stream()
                .map(user -> userMapper.mapToUserWithDetailsDto(user))
                .toList();
    }



    @GetMapping("/search")
    public List<UserDto> searchUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phonePrefix,
            @RequestParam(required = false) String city) {

        return userService.searchActiveUsers(name, phonePrefix, city)
                .stream()
                .map(user -> userMapper.mapToUserDto(user))
                .toList();
    }
    @GetMapping("/pagination/simple")
    public Page<UserDto> getUsersSimplePagination(Pageable pageable) {
        return userService.getUsers(pageable)
                .map(userMapper::mapToUserDto);
    }

    @GetMapping("/pagination/jpql")
    public Page<UserDto> getUsersJpqlPagination(Pageable pageable) {
        return userService.getUsersJpql(pageable)
                .map(userMapper::mapToUserDto);
    }

    @GetMapping("/pagination/native")
    public Page<UserDto> getUsersNativePagination(Pageable pageable) {
        return userService.getUsersNative(pageable)
                .map(userMapper::mapToUserDto);
    }

    @GetMapping("/pagination/criteria")
    public Page<UserDto> getUsersCriteriaPagination(Pageable pageable) {
        return userService.getUsersCriteria(pageable)
                .map(userMapper::mapToUserDto);
    }

    @GetMapping("/pagination/join-fetch")
    public Page<UserWithDetailsDto> getUsersJoinFetchPagination(
            Pageable pageable) {
        return userService.getUsersJoinFetch(pageable)
                .map(userMapper::mapToUserWithDetailsDto);
    }




}