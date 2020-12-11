package com.javamentor.qa.platform.security.util;

import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.security.dto.PrincipalDto;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class SecurityHelper implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    public UserDetails loadUserByUsername(String username) {
        return userService.getUserByEmail(username).get();
    }

    public PrincipalDto getPrincipal () {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    User user = userService.getUserByEmail(authentication.getName()).get();
        return new PrincipalDto(
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getImageLink(),
                user.getRole().getName());
    }

    public Authentication getAuthentication(String username, String password) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
    }
}
