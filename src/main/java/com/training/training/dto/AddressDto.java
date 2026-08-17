package com.training.training.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddressDto {

    private Long id;
    private String city;
    private String street;
    private String buildingNumber;
}