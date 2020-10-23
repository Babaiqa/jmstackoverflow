package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.RoleDao;
import com.javamentor.qa.platform.models.entity.user.Role;
import com.javamentor.qa.platform.service.abstracts.model.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImp extends ReadWriteServiceImpl<Role, String> implements RoleService {

    @Autowired
    public RoleServiceImp(RoleDao roleDao) {
        super(roleDao);
    }
}
