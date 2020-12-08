package com.javamentor.qa.platform.security.controller;

import com.javamentor.qa.platform.security.dto.UserAuthorizationDto;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/auth/")
@Api(value = "AuthenticationApi")
public class AuthenticationController {

    @GetMapping(value = "token")
    public ResponseEntity<?> getUserAuthorization(UserAuthorizationDto userAuthorizationDto) {
//   return token
    return null;
    }

    @GetMapping(value = "principal")
    public ResponseEntity<?> getPrincipalUser() {
//        return principalDto
        return null;
    }
}
