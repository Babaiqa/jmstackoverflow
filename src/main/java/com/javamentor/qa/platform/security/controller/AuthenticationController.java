package com.javamentor.qa.platform.security.controller;

import com.javamentor.qa.platform.security.dto.UserAuthorizationDto;
import com.javamentor.qa.platform.security.jwt.JwtUtils;
import com.javamentor.qa.platform.security.response.JwtResponse;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/auth/")
@Api(value = "AuthenticationApi")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Value("${rest.app.jwtType}")
    private String jwtType;

    @PostMapping(value = "token")
    public ResponseEntity<?> getUserAuthorization(@RequestBody UserAuthorizationDto auth) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(auth.getUsername(), auth.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok(jwtUtils.generateJwtToken(authentication));
    }

    @GetMapping(value = "principal")
    public ResponseEntity<?> getPrincipalUser() {
//        return principalDto
        return null;
    }

}
