package com.javamentor.qa.platform.security.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/logout/")
@Api(value = "LogoutApi")
public class LogoutController {

    @GetMapping
    public void UserLogout() {

    }
}
