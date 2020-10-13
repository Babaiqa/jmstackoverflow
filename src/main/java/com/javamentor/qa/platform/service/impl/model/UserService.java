package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.impl.model.ReadOnlyDaoImpl;
import com.javamentor.qa.platform.dao.impl.model.ReadWriteDaoImpl;
import com.javamentor.qa.platform.dao.impl.model.UserDao;
import com.javamentor.qa.platform.models.entity.user.User;

public class UserService extends ReadWriteServiceImpl<User, Long> {

    public UserService(ReadOnlyDaoImpl readOnlyDao, ReadWriteDaoImpl<User, Long> readWriteDao) {
        super(readOnlyDao, readWriteDao);
    }
    
}
