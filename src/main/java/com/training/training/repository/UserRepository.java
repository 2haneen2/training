package com.training.training.repository;

import com.training.training.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {



    //************************************ - Derived Query - *****************************************************
    boolean existsByPhoneNumberAndDeletedFalse(String phoneNumber);

    boolean existsByPhoneNumberAndIdNotAndDeletedFalse(String phoneNumber,Long id);

    Optional<User> findByIdAndDeletedFalse(Long id);

    //************************************ - JPQL Query - *****************************************************
    @Query("""
             SELECT distinct u 
             FROM User u 
             Left join fetch  u.addresses
             where u.deleted = false """)
    List<User> findAllActiveUsersWithAddresses();

    @Query("""
       SELECT DISTINCT u
       FROM User u
       JOIN FETCH u.addresses a
       WHERE LOWER(a.city) = LOWER(:city)
       AND u.deleted = false
       AND a.deleted = false
       """)
    List<User> findByCityWithAddresses(@Param("city") String city);

    //************************************ Native Query ************************************

    @Query(value = """
       SELECT *
       FROM users
       WHERE deleted = false
       AND phone_number LIKE CONCAT(:prefix, '%')
       """, nativeQuery = true)
    List<User> findActiveUsersByPhonePrefix(
            @Param("prefix") String prefix
    );

}