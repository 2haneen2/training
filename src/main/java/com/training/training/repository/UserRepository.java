package com.training.training.repository;

import com.training.training.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepository extends JpaRepository<User, Long> {



    //************************************ - Derived Query - *****************************************************
    Boolean existsByPhoneNumberAndDeletedFalse(String phoneNumber);

    Boolean existsByPhoneNumberAndIdNotAndDeletedFalse(String phoneNumber,Long id);

    Optional<User> findByIdAndDeletedFalse(Long id);

    Page<User> findAllByDeletedFalse(Pageable pageable);

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
       WHERE a.city ILIKE :city
       AND u.deleted = false
       AND a.deleted = false
       """)
    List<User> findByCityWithAddresses(@Param("city") String city);

    //************************************ Native Query ************************************

    @Query(value = """
       SELECT *
       FROM user
       WHERE deleted = false
       AND phone_number LIKE CONCAT(:prefix, '%')
       """, nativeQuery = true)
    List<User> findActiveUsersByPhonePrefix(
            @Param("prefix") String prefix
    );

    @Query(
            value = """
                SELECT u
                FROM User u
                WHERE u.deleted = false
                ORDER BY u.id
                """,
            countQuery = """
                SELECT COUNT(u)
                FROM User u
                WHERE u.deleted = false
                """
    )
    Page<User> findAllActiveUsersJpql(Pageable pageable);

}