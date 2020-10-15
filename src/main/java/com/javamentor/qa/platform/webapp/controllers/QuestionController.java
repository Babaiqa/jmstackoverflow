package com.javamentor.qa.platform.webapp.controllers;

import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.service.impl.model.QuestionServise;
import com.javamentor.qa.platform.webapp.converters.QuestionConverter;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("/api/question/")
@Api(value = "QuestionApi")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionServise questionServise;
    private final QuestionConverter questionConverter;


    // Examples for Swagger
    @GetMapping("{id}")
    @ApiOperation(value = "QuestionDto", response = String.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returns the QuestionDto.", response = QuestionDto.class),
            @ApiResponse(code = 400, message = " throw  EntityNotFoundException(\"Question not found\")", response = String.class)
    })

    public ResponseEntity<QuestionDto> getUserById(
            @ApiParam(name = "id", value = "type Long", required = true, example = "0")
            @PathVariable Long id) {
        Optional<Question> question = questionServise.getById(id);

        if (question.isPresent()) {
            return ResponseEntity.ok(questionConverter.questionToQuestionDTO(question.get()));

        } else {
            throw new EntityNotFoundException("Question not found");
        }
    }
}
