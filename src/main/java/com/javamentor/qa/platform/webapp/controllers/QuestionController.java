package com.javamentor.qa.platform.webapp.controllers;

import com.javamentor.qa.platform.service.impl.model.QuestionService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/question/")
@Api(value = "QuestionApi")
public class QuestionController {
    private QuestionService questionService;

@Autowired
public QuestionController(QuestionService questionService) {
    this.questionService = questionService;
}

    @DeleteMapping("/{id}/delete")
    @ApiOperation(value = "Delete question", response = String.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Deletes the question.", response = String.class),
            @ApiResponse(code = 400, message = "Wrong ID",response = String.class)
    })
    public ResponseEntity<String> deleteQuestionById(@ApiParam(name = "id") @PathVariable Long id) {

        if(questionService.existsById(id)) {
            questionService.delete(questionService.getById(id).get());
            return ResponseEntity.ok("Question was deleted");
        } else {
            return ResponseEntity.badRequest().body("Wrong ID");
        }
    }
}
