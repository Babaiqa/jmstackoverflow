package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.UserDtoDao;
import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserDtoDaoImpl implements UserDtoDao {
    @PersistenceContext
    private final EntityManager entityManager;

    public UserDtoDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public UserDto getUserById(long id) {
        UserDto user = entityManager.createQuery(
                "select new com.javamentor.qa.platform.models.dto.UserDto(u.id,  u.email, u.fullName, u.imageLink, u.reputationCount) from " +
                        User.class.getName()  + " u where u.id = ?1",  UserDto.class
        )
                .setParameter(1, id)
                .setMaxResults(1).getSingleResult();

        return user;
    }
}

