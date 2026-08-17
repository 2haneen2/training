package com.training.training.repository;

import com.training.training.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import com.training.training.entity.Address;
import jakarta.persistence.criteria.Join;
import org.springframework.util.StringUtils;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserCustomRepository {

    private final EntityManager entityManager;

    public UserCustomRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<User> searchActiveUsers(
            String name,
            String phonePrefix,
            String city) {

        HibernateCriteriaBuilder criteriaBuilder =
                (HibernateCriteriaBuilder) entityManager.getCriteriaBuilder();

        CriteriaQuery<User> criteriaQuery =
                criteriaBuilder.createQuery(User.class);

        Root<User> userRoot =
                criteriaQuery.from(User.class);

        List<Predicate> predicates = new ArrayList<>();


        predicates.add(
                criteriaBuilder.isFalse(userRoot.get("deleted"))
        );


        if (StringUtils.hasText(name)) {
            predicates.add(
                    criteriaBuilder.ilike(
                            userRoot.<String>get("name"),
                            "%" + name + "%"
                    )
            );
        }


        if (StringUtils.hasText(phonePrefix)) {
            predicates.add(
                    criteriaBuilder.like(
                            userRoot.<String>get("phoneNumber"),
                            phonePrefix + "%"
                    )
            );
        }

        if (StringUtils.hasText(city)) {

            Join<User, Address> addressJoin =
                    userRoot.join("address");

            predicates.add(
                    criteriaBuilder.ilike(
                            addressJoin.<String>get("city"),
                            city
                    )
            );

            predicates.add(
                    criteriaBuilder.isFalse(
                            addressJoin.get("deleted")
                    )
            );
        }

        criteriaQuery.select(userRoot)
                .distinct(true)
                .where(criteriaBuilder.and(
                        predicates.toArray(new Predicate[0])
                ));

        return entityManager.createQuery(criteriaQuery)
                .getResultList();
    }
}