package com.javamentor.qa.platform.webapp.controllers;

import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.service.abstracts.dto.QuestionDtoService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("/api/question/")
@Api(value = "QuestionApi")
public class QuestionController {
    private QuestionService questionService;
    private final QuestionDtoService questionDtoService;

    private final int MAX_ITEMS_ON_PAGE = 100;

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

    @GetMapping(
            value = "/api/question",
            params = {"page", "size"}
    )
    @ApiOperation(value = "Return object(PageDto<QuestionDto, Object>)", response = String.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returns the pagination List<QuestionDto>", response = List.class),
    })
    public ResponseEntity<?> findPagination(

            @ApiParam(name = "page", value = "Number Page. type int", required = true, example = "1")
            @RequestParam("page") int page,
            @ApiParam(name = "size", value = "Number of entries per page.Type int." +
                    " Максимальное количество записей на странице" + MAX_ITEMS_ON_PAGE,
                    example = "10")
            @RequestParam("size") int size) {

        if (page <= 0 || size <= 0 || size > MAX_ITEMS_ON_PAGE) {
            return ResponseEntity.badRequest().body("Номер страницы и размер должны быть " +
                    "положительными. Максимальное количество записей на странице " + MAX_ITEMS_ON_PAGE);
        }
        PageDto<QuestionDto, Object> resultPage = questionDtoService.getPagination(page, size);

        return ResponseEntity.ok(resultPage);
    }

}




