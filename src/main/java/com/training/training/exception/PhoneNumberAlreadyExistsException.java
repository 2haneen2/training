package com.training.training.exception;

import lombok.Getter;

@Getter
public class PhoneNumberAlreadyExistsException extends RuntimeException {

    private final String phoneNumber;

    public PhoneNumberAlreadyExistsException(String phoneNumber) {
        super("Phone number already exists");
        this.phoneNumber = phoneNumber;
    }
}