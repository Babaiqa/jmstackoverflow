package com.javamentor.qa.platform.webapp.controllers;


import com.javamentor.qa.platform.models.dto.CommentDto;
import com.javamentor.qa.platform.models.entity.question.CommentQuestion;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.CommentDtoService;
import com.javamentor.qa.platform.service.abstracts.model.CommentQuestionService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Validated
@RequestMapping("/api/comment/")
@Api(value = "CommentApi")
public class CommentController {

    private final QuestionService questionService;
    private final CommentQuestionService commentQuestionService;
    private final CommentDtoService commentDtoService;


    public CommentController(QuestionService questionService, CommentQuestionService commentQuestionService,
                             CommentDtoService commentDtoService) {
        this.questionService = questionService;
        this.commentQuestionService = commentQuestionService;
        this.commentDtoService = commentDtoService;
    }

    @PostMapping("question/{questionId}")
    @ApiOperation(value = "Add comment", notes = "This method Add comment to question and return CommentDto")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Comment was added", response = CommentDto.class),
            @ApiResponse(code = 400, message = "Question or user not found", response = String.class)
    })

    public ResponseEntity<?> addCommentToQuestion(
            @ApiParam(name = "questionId", value = "questionId. Type long", required = true, example = "1")
            @PathVariable Long questionId,
            @AuthenticationPrincipal User user,

            @ApiParam(name = "text", value = "Text of comment. Type string", required = true, example = "Some comment")
            @RequestBody String commentText) {


        Optional<Question> question = questionService.getById(questionId);
        if (!question.isPresent()) {
            return ResponseEntity.badRequest().body("Question not found");
        }

        CommentQuestion commentQuestion = commentQuestionService.addCommentToQuestion(commentText, question.get(), user);
        Optional<CommentDto> commentDto = commentDtoService.getCommentDtoById(commentQuestion.getComment().getId());

        return commentDto.isPresent() ? ResponseEntity.ok(commentDto.get()) :
                ResponseEntity.badRequest().body("Failed to save");
    }
}
