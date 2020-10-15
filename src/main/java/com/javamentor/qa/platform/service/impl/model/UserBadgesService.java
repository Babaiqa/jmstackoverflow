package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.impl.model.UserBadgesDao;
import com.javamentor.qa.platform.models.entity.user.UserBadges;
import org.springframework.stereotype.Service;

@Service
public class UserBadgesService extends ReadWriteServiceImpl <UserBadges, Long> {
    public UserBadgesService(UserBadgesDao userBadgesDao){
        super(userBadgesDao);
    }
}
