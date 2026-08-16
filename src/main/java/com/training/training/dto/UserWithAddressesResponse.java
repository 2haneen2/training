package com.training.training.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@Getter
@AllArgsConstructor
public class UserWithAddressesResponse {

    private Long id;
    private String name;
    private String phoneNumber;
    private List<AddressResponse> addresses;
}