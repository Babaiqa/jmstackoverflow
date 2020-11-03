package com.javamentor.qa.platform.webapp.controllers;


import com.javamentor.qa.platform.models.dto.CommentDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.CommentQuestionService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Validated
@RequestMapping("/api/comment/")
@Api(value = "CommentApi")
public class CommentController {

    private final UserService userService;
    private final QuestionService questionService;
    private final CommentQuestionService commentQuestionService;

    @Autowired
    public CommentController(UserService userService, QuestionService questionService, CommentQuestionService commentQuestionService) {
        this.userService = userService;
        this.questionService = questionService;
        this.commentQuestionService = commentQuestionService;
    }


    @PostMapping("question/{questionId}")
    @ApiOperation(value = "Add comment", notes = "This method Add comment to question and return CommentDto")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Comment was added", response = CommentDto.class),
            @ApiResponse(code = 400, message = "Question or user not found", response = String.class)
    })
    @ApiModelProperty(value = "Text", name = "commentText",
            example = "This is my comment.")
    public ResponseEntity<?> addCommentToQuestion(
            @ApiParam(name = "questionId", value = "questionId. Type long", required = true, example = "1")
            @PathVariable Long questionId,
            @ApiParam(name = "userId", value = "userId. Type long", required = true, example = "1")
            @RequestParam Long userId,
            @ApiParam(name = "text", value = "userId. Type long", required = true, example = "1")
            @RequestBody String commentText) {

        Optional<User> user = userService.getById(userId);
        if (!user.isPresent()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        Optional<Question> question = questionService.getById(questionId);
        if (!question.isPresent()) {
            return ResponseEntity.badRequest().body("Question not found");
        }

        Optional<CommentDto> commentDto = commentQuestionService.addCommentToQuestion(commentText, question.get(), user.get());


        return commentDto.isPresent() ? ResponseEntity.ok(commentDto.get()) :
                ResponseEntity.badRequest().body("Failed to save");
    }
}
