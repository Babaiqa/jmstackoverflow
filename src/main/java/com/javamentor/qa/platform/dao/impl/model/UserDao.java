package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserDao extends ReadWriteDaoImpl<User, Long> {
    @PersistenceContext
    private EntityManager entityManager;

    public User getUserByEmail(String email) {
        String query = "SELECT  from User  where email = " + email;
        User user;
        user = (User) entityManager.createQuery(query).getSingleResult();
        return user;
    }
}
