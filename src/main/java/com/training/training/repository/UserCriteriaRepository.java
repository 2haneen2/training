package com.training.training.repository;

import com.training.training.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserCriteriaRepository {

    private final EntityManager entityManager;

    public UserCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<User> searchActiveUsers(
            String name,
            String phonePrefix) {

        CriteriaBuilder criteriaBuilder =
                entityManager.getCriteriaBuilder();

        CriteriaQuery<User> criteriaQuery =
                criteriaBuilder.createQuery(User.class);

        Root<User> userRoot =
                criteriaQuery.from(User.class);

        List<Predicate> predicates = new ArrayList<>();


        predicates.add(
                criteriaBuilder.isFalse(userRoot.get("deleted"))
        );


        if (name != null && !name.isBlank()) {
            predicates.add(
                    criteriaBuilder.like(
                            criteriaBuilder.lower(
                                    userRoot.<String>get("name")
                            ),
                            "%" + name.toLowerCase() + "%"
                    )
            );
        }


        if (phonePrefix != null && !phonePrefix.isBlank()) {
            predicates.add(
                    criteriaBuilder.like(
                            userRoot.<String>get("phoneNumber"),
                            phonePrefix + "%"
                    )
            );
        }

        criteriaQuery.select(userRoot)
                .where(criteriaBuilder.and(
                        predicates.toArray(new Predicate[0])
                ));

        return entityManager.createQuery(criteriaQuery)
                .getResultList();
    }
}