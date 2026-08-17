package com.training.training.mapper;
import com.training.training.dto.CreateUserRequest;
import com.training.training.dto.UserDto;
import com.training.training.entity.User;
import org.springframework.stereotype.Component;
import com.training.training.dto.AddressDto;
import com.training.training.dto.UserWithDetailsDto;
import com.training.training.entity.Address;
import java.util.List;
import com.training.training.dto.UpdateUserRequest;
@Component
public class UserMapper {

    public User mapToUser(CreateUserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setPhoneNumber(request.getPhoneNumber());
        return user;
    }
    public User mapToUser(UpdateUserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setPhoneNumber(request.getPhoneNumber());
        return user;
    }

    public UserDto mapToUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getPhoneNumber()
        );
    }
    public AddressDto toAddressResponse(Address address) {
        return new AddressDto(
                address.getId(),
                address.getCity(),
                address.getStreet(),
                address.getBuildingNumber()
        );
    }
    public UserWithDetailsDto mapToUserWithDetailsDto(User user) {

        List<AddressDto> addresses = user.getAddresses()
                .stream()
                .filter(address -> !address.isDeleted())
                .map(address -> toAddressResponse(address))
                .toList();

        return new UserWithDetailsDto(
                user.getId(),
                user.getName(),
                user.getPhoneNumber(),
                addresses
        );
    }
}