package com.javamentor.qa.platform.webapp.controllers;

import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.service.impl.model.QuestionServiceImpl;
import com.javamentor.qa.platform.webapp.converters.QuestionConverter;
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
    private QuestionServiceImpl questionServiceImpl;
    private QuestionDto questionDto;
    private QuestionConverter questionConverter;

@Autowired
public QuestionController(QuestionServiceImpl questionServiceImpl) {
    this.questionServiceImpl = questionServiceImpl;
}

    @DeleteMapping("/{id}/delete")
    @ApiOperation(value = "Delete question", response = String.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Deletes the question.", response = String.class),
            @ApiResponse(code = 400, message = "Wrong ID",response = String.class)
    })
    public ResponseEntity<String> deleteQuestionById(@ApiParam(name = "id") @PathVariable Long id) {

        if(Boolean.TRUE.equals(questionServiceImpl.existsById(id)))
        {
            questionServiceImpl.deleteById(id);
            return ResponseEntity.ok("Question was deleted");
        } else {
            return ResponseEntity.badRequest().body("Wrong ID");
        }
    }

    @GetMapping("{id}")
    @ApiOperation(value = "Return message(Object)", response = String.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returns the object.", response = String.class),
            @ApiResponse(code = 400, message = "Wrong ID",response = String.class)
    })
    public  ResponseEntity<String> getQuestionById(
            @ApiParam(name="id",value="type Long(or other described)", required = true, example="0")
            @PathVariable Long id){

        return id!=null ? ResponseEntity.ok("Swagger work"):
                ResponseEntity.badRequest()
                        .body("Wrong ID");
    }
}
