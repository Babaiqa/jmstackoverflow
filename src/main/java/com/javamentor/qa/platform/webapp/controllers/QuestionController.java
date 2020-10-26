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
    @Autowired
    private final QuestionDtoService questionDtoService;

    private QuestionConverterImpl questionConverterImpl = new QuestionConverterImpl();

    @Autowired
    private QuestionService questionService;

    public QuestionController(QuestionDtoService questionDtoService) {
        this.questionDtoService = questionDtoService;
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

        return questionDto.isPresent()?ResponseEntity.ok(questionDto.get()):
                ResponseEntity.badRequest().body("Question not found");
    }

//    @PostMapping("add")
////    public void addQuestion(@RequestParam Question question ) {
//    public void addQuestion(@RequestParam ("question") QuestionDto question) {
////        Question question = new Question();
////        question = questionConverter.questionDtoToQuestion(addQuestionDto);
////        questionService.persist(question);
////        System.out.println(question.getId());
////        return question.getId() ;
////        Optional<QuestionDto> questionDto = questionDtoService.getQuestionDtoById(694L);
//        System.out.println(question);
////        if (questionDto.isPresent()) {
////            return ResponseEntity.ok(questionDto.get());
////        } else {
////            return ResponseEntity.badRequest().body("Question not found");
////        }
//    }


    @PostMapping("add")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public QuestionDto addQuestion(@RequestBody QuestionDto questionDto) {
//    public QuestionDto addQuestion(@RequestBody String tx) {
//        System.out.println(tx);
        Question question = questionConverterImpl.questionDtoToQuestion(questionDto);
        questionService.persist(question);
        QuestionDto addQuestionDto = questionConverterImpl.questionToQuestionDto(question);
        return addQuestionDto;
    }


}
