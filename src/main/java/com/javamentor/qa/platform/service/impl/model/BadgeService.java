package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.impl.model.BadgeDaoImpl;

import com.javamentor.qa.platform.models.entity.Badge;

import org.springframework.stereotype.Service;

@Service
public class BadgeService extends ReadWriteServiceImpl<Badge, Long>{

    public BadgeService(BadgeDaoImpl badgeDaoImpl) {
        super(badgeDaoImpl);
    }
}
