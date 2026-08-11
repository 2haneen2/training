package com.training.training.exception;

import com.training.training.entity.User;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message){

        super(message);
    }
}
