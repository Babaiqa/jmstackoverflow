package com.javamentor.qa.platform.webapp.controllers;

import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.service.abstracts.dto.QuestionDtoService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;

import com.javamentor.qa.platform.webapp.converters.QuestionConverter;
import com.javamentor.qa.platform.webapp.converters.QuestionConverterImpl;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Validated
@RequestMapping("/api/question/")
@Api(value = "QuestionApi")
public class QuestionController {
    private QuestionService questionService;
    private final QuestionDtoService questionDtoService;

    private QuestionConverter questionConverter = new QuestionConverterImpl();

    @Autowired
    public QuestionController(QuestionService questionService, QuestionDtoService questionDtoService) {
        this.questionService = questionService;
        this.questionDtoService = questionDtoService;
    }

    @DeleteMapping("/{id}/delete")
    @ApiOperation(value = "Delete question", response = String.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Deletes the question.", response = String.class),
            @ApiResponse(code = 400, message = "Wrong ID", response = String.class)
    })
    public ResponseEntity<String> deleteQuestionById(@ApiParam(name = "id") @PathVariable Long id) {
        if (Boolean.TRUE.equals(questionService.existsById(id))) {
            questionService.delete(questionService.getById(id).get());
            return ResponseEntity.ok("Question was deleted");
        } else {
            return ResponseEntity.badRequest().body("Wrong ID");
        }
    }

    @GetMapping("{id}")
    @ApiOperation(value = "get QuestionDto", response = String.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returns the QuestionDto", response = QuestionDto.class),
            @ApiResponse(code = 400, message = "Question not found", response = String.class)
    })

    public ResponseEntity<?> getQuestionById(
            @ApiParam(name = "id", value = "type Long", required = true, example = "0")
            @PathVariable Long id) {

        Optional<QuestionDto> questionDto = questionDtoService.getQuestionDtoById(id);

        return questionDto.isPresent() ? ResponseEntity.ok(questionDto.get()) :
                ResponseEntity.badRequest().body("Question not found");
    }

    @PostMapping("add")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public QuestionDto addQuestion(@RequestBody QuestionDto questionDto) {
        Question question = questionConverter.questionDtoToQuestion(questionDto);
        questionService.persist(question);
        return  questionConverter.questionToQuestionDto(question);
    }

}




