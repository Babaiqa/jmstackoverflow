package com.javamentor.qa.platform.security.util;

import com.javamentor.qa.platform.service.abstracts.model.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SecurityHelperIml implements SecurityHelper{

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userService.getUserByEmail(username).get();
    }
}
