package com.training.training.mapper;
import com.training.training.dto.UserRequest;
import com.training.training.dto.UserResponse;
import com.training.training.entity.User;
import org.springframework.stereotype.Component;
import com.training.training.dto.AddressResponse;
import com.training.training.dto.UserWithAddressesResponse;
import com.training.training.entity.Address;
import java.util.List;

@Component
public class UserMapper {

    public User toEntity(UserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setPhoneNumber(request.getPhoneNumber());
        return user;
    }

    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getPhoneNumber()
        );
    }
    public AddressResponse toAddressResponse(Address address) {
        return new AddressResponse(
                address.getId(),
                address.getCity(),
                address.getStreet(),
                address.getBuildingNumber()
        );
    }
    public UserWithAddressesResponse toWithAddressesResponse(User user) {

        List<AddressResponse> addresses = user.getAddresses()
                .stream()
                .filter(address -> !address.isDeleted())
                .map(address -> toAddressResponse(address))
                .toList();

        return new UserWithAddressesResponse(
                user.getId(),
                user.getName(),
                user.getPhoneNumber(),
                addresses
        );
    }
}