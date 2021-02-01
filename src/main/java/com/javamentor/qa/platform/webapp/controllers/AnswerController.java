package com.javamentor.qa.platform.webapp.controllers;

import com.javamentor.qa.platform.models.dto.CommentDto;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.CommentAnswer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.security.util.SecurityHelper;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.abstracts.model.CommentAnswerService;
import com.javamentor.qa.platform.service.abstracts.model.CommentService;
import com.javamentor.qa.platform.webapp.converters.CommentConverter;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Validated
@RequestMapping("api/answer")
@Api(value = "AnswerApi")
public class AnswerController {
    private final AnswerService answerService;
    private final CommentAnswerService commentAnswerService;
    private final CommentConverter commentConverter;
    private final SecurityHelper securityHelper;

    @Autowired
    public AnswerController(AnswerService answerService,
                            CommentAnswerService commentAnswerService,
                            CommentConverter commentConverter,
                            SecurityHelper securityHelper) {
        this.answerService = answerService;
        this.commentAnswerService = commentAnswerService;
        this.commentConverter = commentConverter;
        this.securityHelper = securityHelper;
    }

    @PostMapping("{answerId}/comment")
    @ApiOperation(value = "Add comment", notes = "This method Add comment to answer and return CommentDto")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Comment was added", response = CommentDto.class),
            @ApiResponse(code = 400, message = "Answer or user not found", response = String.class)
    })
    public ResponseEntity<?> addCommentToAnswer(
            @ApiParam(name = "AnswerId", value = "AnswerId. Type long", required = true, example = "1")
            @PathVariable Long answerId,
            @ApiParam(name = "text", value = "Text of comment. Type string", required = true, example = "Some comment")
            @RequestBody String commentText) {

        User user = securityHelper.getPrincipal();

        Optional<Answer> answer = answerService.getById(answerId);
        if (!answer.isPresent()) {
            return ResponseEntity.badRequest().body("Answer not found");
        }

        CommentAnswer commentAnswer = commentAnswerService.addCommentToAnswer(commentText, answer.get(), user);

        return ResponseEntity.ok(commentConverter.commentToCommentDTO(commentAnswer));
    }
}
