package com.javamentor.qa.platform.security.util;

import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface SecurityHelper extends UserDetailsService {
    UserDetails loadUserByUsername(String username);
}
