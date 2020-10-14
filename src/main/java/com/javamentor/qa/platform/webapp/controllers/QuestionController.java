package com.javamentor.qa.platform.webapp.controllers;

import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/question/")
@Api(value = "QuestionApi")
public class QuestionController {
}
