package com.training.training.service;
import com.training.training.repository.UserCriteriaRepository;
import com.training.training.exception.UserNotFoundException;
import com.training.training.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.training.training.entity.User;
import com.training.training.exception.PhoneNumberAlreadyExistsException;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserCriteriaRepository userCriteriaRepository;

    //constructor

    public UserService(
            UserRepository userRepository,
            UserCriteriaRepository userCriteriaRepository) {

        this.userRepository = userRepository;
        this.userCriteriaRepository = userCriteriaRepository;
    }

    //******************************************  Add User  **********************************************************
    public User addUser(User user) {

        if (userRepository.existsByPhoneNumberAndDeletedFalse(user.getPhoneNumber())) {
            throw new PhoneNumberAlreadyExistsException("Phone number already exists");
        }
        return userRepository.save(user);
    }

    //******************************************  Update User  *********************************************************
    public User updateUser(Long id, User request) {

        User existingUser = userRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        if (userRepository.existsByPhoneNumberAndIdNotAndDeletedFalse(request.getPhoneNumber(), id)) {
            throw new PhoneNumberAlreadyExistsException("Phone number already exists");
        }

        existingUser.setName(request.getName());
        existingUser.setPhoneNumber(request.getPhoneNumber());

        return userRepository.save(existingUser);
    }

    //******************************************  Delete User  *********************************************************
    public void deleteUser(Long id) {

        User existingUser = userRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        existingUser.setDeleted(true);
        userRepository.save(existingUser);
    }

    public User getUser(Long id) {

        return userRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
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

    public List<User> searchActiveUsers(String name,String phonePrefix) {

        return userCriteriaRepository.searchActiveUsers(name,phonePrefix);
    }




}