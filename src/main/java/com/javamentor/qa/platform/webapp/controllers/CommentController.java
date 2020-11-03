package com.javamentor.qa.platform.webapp.controllers;


import com.javamentor.qa.platform.dao.abstracts.dto.UserDtoDao;
import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.UserDtoService;
import com.javamentor.qa.platform.service.abstracts.model.CommentQuestionService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.service.impl.model.CommentQuestionServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Validated
@RequestMapping("/api/comment/")
///api/comment/question/{questionId}
public class CommentController {

    private final UserDtoService userDtoService;
    private final UserService userService;
    private final QuestionService questionService;
    private final CommentQuestionService commentQuestionService;


    @Autowired

    public CommentController(UserDtoService userDtoService, UserService userService, QuestionService questionService, CommentQuestionService commentQuestionService) {
        this.userDtoService = userDtoService;
        this.userService = userService;
        this.questionService = questionService;
        this.commentQuestionService = commentQuestionService;
    }





    @PostMapping(value = "question/{questionId}", params = {"page", "size"})
    @ApiOperation(value = "Add comment to question and return CommentDto")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Tags were added", response = String.class),
            @ApiResponse(code = 400, message = "Question not found", response = String.class)
    })
    public ResponseEntity<?> addCommentToQuestion(@PathVariable Long questionId, @RequestParam Long userId,
                                                  @RequestBody String commentText) {

        Optional<User> user = userService.getById(userId);
        if(!user.isPresent()){
            return ResponseEntity.badRequest().body("User not found");
        }

        Optional<Question> question=questionService.getById(questionId);
        if(!question.isPresent()){
            return ResponseEntity.badRequest().body("Question not found");
        }

        commentQuestionService(commentText,question,user);



    }
}
