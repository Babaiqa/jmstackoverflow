package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.impl.model.UserDao;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService extends ReadWriteServiceImpl<User, Long> {

    @Autowired
    public UserService(UserDao userDao) {
        super(userDao);
    }
}
