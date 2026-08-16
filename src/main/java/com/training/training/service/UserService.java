package com.training.training.service;
import com.training.training.repository.UserCustomRepository;
import com.training.training.exception.UserNotFoundException;
import com.training.training.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.training.training.entity.User;
import com.training.training.exception.PhoneNumberAlreadyExistsException;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserCustomRepository userCustomRepository;

    //constructor

    public UserService(
            UserRepository userRepository,
            UserCustomRepository userCustomRepository) {

        this.userRepository = userRepository;
        this.userCustomRepository = userCustomRepository;
    }

    //******************************************  Add User  **********************************************************
    public User createUser(User user) {

        if (userRepository.existsByPhoneNumberAndDeletedFalse(user.getPhoneNumber())) {
            throw new PhoneNumberAlreadyExistsException(
                    user.getPhoneNumber()
            );
        }
        return userRepository.save(user);
    }

    //******************************************  Update User  *********************************************************
    public User updateUser(Long id, User request) {

        User existingUser = userRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new UserNotFoundException(id));

        if (userRepository.existsByPhoneNumberAndIdNotAndDeletedFalse(request.getPhoneNumber(), id)) {
            throw new PhoneNumberAlreadyExistsException(
                    request.getPhoneNumber()
            );
        }

        existingUser.setName(request.getName());
        existingUser.setPhoneNumber(request.getPhoneNumber());

        return userRepository.save(existingUser);
    }

    //******************************************  Delete User  *********************************************************
    public void deleteUser(Long id) {

        User existingUser = userRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new UserNotFoundException(id));

        existingUser.setDeleted(true);
        userRepository.save(existingUser);
    }

    public User getUser(Long id) {

        return userRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public List<User> getAllActiveUsersWithAddresses() {
        return userRepository.findAllActiveUsersWithAddresses();
    }

    public List<User> getUsersByCityWithAddresses(String city) {
        return userRepository.findByCityWithAddresses(city);
    }

    public List<User> getActiveUsersByPhonePrefix(String prefix) {
        return userRepository.findActiveUsersByPhonePrefix(prefix);
    }

    public List<User> searchActiveUsers(
            String name,
            String phonePrefix,
            String city) {

        return userCustomRepository.searchActiveUsers(
                name,
                phonePrefix,
                city
        );
    }

    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAllByDeletedFalse(pageable);
    }

    public Page<User> getUsersJpql(Pageable pageable) {
        return userRepository.findAllActiveUsersJpql(pageable);
    }

    public Page<User> getUsersNative(Pageable pageable) {
        return userRepository.findAllActiveUsersNative(pageable);
    }


}