package com.training.training.dto;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class UserResponse {

    private Long id;

    private String name;

    private String phoneNumber;

    public UserResponse(Long id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}