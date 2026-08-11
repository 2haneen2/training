package com.training.training.service;

import com.training.training.dto.UserRequest;
import com.training.training.dto.UserResponse;
import com.training.training.exception.UserNotFoundException;
import com.training.training.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.training.training.entity.User;


@Service
public class UserService {

    private final UserRepository userRepository;
    //constructor
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    //******************************************  Add User  **********************************************************
    public UserResponse addUser(UserRequest request) {

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        User savedUser = userRepository.save(user);

        UserResponse response = new UserResponse();

        response.setId(savedUser.getId());
        response.setName(savedUser.getName());
        response.setEmail(savedUser.getEmail());

        return response;
    }
    //******************************************  Update User  *********************************************************
    public UserResponse updateUser( Long id,  UserRequest  request) {

        User existingUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id ));
        existingUser.setName(request.getName());
        existingUser.setEmail(request.getEmail());

        User savedUser = userRepository.save(existingUser);

        UserResponse response = new UserResponse();

        response.setId(savedUser.getId());
        response.setName(savedUser.getName());
        response.setEmail(savedUser.getEmail());

        return response;
    }
    //******************************************  Delete User  *********************************************************
    public void deleteUser(Long id) {
        User existingUser = userRepository.findById(id).orElseThrow(() ->new UserNotFoundException("User not found with id: " + id ));
        userRepository.delete(existingUser);
    }

    public boolean existsUser(Long id) {
        return userRepository.existsById(id);
    }
}