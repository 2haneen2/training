package com.training.training.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message){

        super(message);
    }
}
