package com.javamentor.qa.platform.webapp.controllers;

import com.javamentor.qa.platform.models.dto.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.models.entity.user.reputation.ReputationType;
import com.javamentor.qa.platform.service.abstracts.dto.QuestionDtoService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.webapp.converters.QuestionConverter;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("/api/moder/")
@Api(value = "ModerApi")
public class ModerController {

    private final QuestionDtoService questionDtoService;
    private final QuestionConverter questionConverter;
    private final QuestionService questionService;

    public ModerController(QuestionDtoService questionDtoService,
                           QuestionConverter questionConverter,
                           QuestionService questionService) {
        this.questionDtoService = questionDtoService;
        this.questionConverter = questionConverter;
        this.questionService = questionService;
    }

    @GetMapping("/question/{id}")
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

    @PutMapping("/question/{id}")
    @ApiOperation(value = "Save the modified question", response = String.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returns the QuestionDto", response = QuestionDto.class),
            @ApiResponse(code = 400, message = "Changes are not saved", response = String.class)
    })
    public ResponseEntity<?> updateQuestionById(
            @Valid @RequestBody QuestionCreateDto questionCreateDto,
            @ApiParam(name = "id", value = "question id, type Long", required = true, example = "0")
            @PathVariable Long id) {

        Optional<Question> questionFromDB = questionService.getById(id);

        if ( !questionFromDB.isPresent() ) {
            return ResponseEntity.badRequest().body("Question not found");
        }

        Question question = questionFromDB.get();
        question.setDescription(questionCreateDto.getDescription());
        question.setTitle(questionCreateDto.getTitle());
        question.setLastUpdateDateTime(LocalDateTime.now());

        questionService.update(question);

        QuestionDto questionDtoNew = questionConverter.questionToQuestionDto(question);
        return ResponseEntity.ok(questionDtoNew);

    }
}
