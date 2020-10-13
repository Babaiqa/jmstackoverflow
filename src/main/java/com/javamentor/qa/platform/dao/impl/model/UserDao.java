package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.models.entity.user.User;

public class UserDao extends ReadWriteDaoImpl<User,Long> {

    @Override
    public void persist(User user) {
        super.persist(user);
    }

    @Override
    public void update(User user) {
        super.update(user);
    }

    @Override
    public void delete(User user) {
        super.delete(user);
    }
}
