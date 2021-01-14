package com.javamentor.qa.platform.webapp.controllers;

import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.util.OnCreate;
import com.javamentor.qa.platform.security.dto.PrincipalDto;
import com.javamentor.qa.platform.security.dto.TokenDto;
import com.javamentor.qa.platform.security.dto.UserAuthorizationDto;
import com.javamentor.qa.platform.security.jwt.JwtUtils;
import com.javamentor.qa.platform.security.util.SecurityHelper;
import com.javamentor.qa.platform.webapp.converters.UserConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@Validated
@RequestMapping("/api/auth/")
@Api(value = "AuthenticationApi")
public class AuthenticationController {

    private final JwtUtils jwtUtils;
    private final SecurityHelper securityHelper;
    private final UserConverter userConverter;

    @Autowired
    public AuthenticationController(JwtUtils jwtUtils, SecurityHelper securityHelper,
                                    UserConverter userConverter) {
        this.jwtUtils = jwtUtils;
        this.securityHelper = securityHelper;
        this.userConverter = userConverter;
    }

    @PostMapping(value = "token")
    @ApiOperation(value = "Authenticated user and return token", response = TokenDto.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Return TokenDto", response = TokenDto.class),
            @ApiResponse(code = 400, message = "Error: User is not Authenticated",response = String.class)
    })
    @Validated(OnCreate.class)
    public ResponseEntity<?> getToken(@Valid @RequestBody UserAuthorizationDto auth) {

        Authentication authentication =
                securityHelper.getAuthentication(auth.getUsername(), auth.getPassword());

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);

        return context.getAuthentication().isAuthenticated() ?
                ResponseEntity.ok(jwtUtils.getTokenDto(authentication)) :
                ResponseEntity.badRequest().body("Error: User is not Authenticated");

    }


    @GetMapping(value = "principal")
    @ApiOperation(value = "get UserDto", response = UserDto.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Return UserDto", response = UserDto.class),
            @ApiResponse(code = 400, message = "Principal not found",response = String.class)
    })
    public ResponseEntity<?> getPrincipalUser() {
        User user = securityHelper.getPrincipal();
        if (user != null) {
//            return ResponseEntity.ok(new PrincipalDto(user.getId(),
//                    user.getEmail(),
//                    user.getFullName(),
//                    user.getImageLink(),
//                    user.getRole().getName()));
            return ResponseEntity.ok(userConverter.userToPrincipalDto(user));
        }
        return ResponseEntity.badRequest().body("Error: User is not found");
    }

}
