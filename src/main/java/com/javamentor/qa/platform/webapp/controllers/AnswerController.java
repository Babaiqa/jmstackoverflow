package com.javamentor.qa.platform.webapp.controllers;

import com.javamentor.qa.platform.models.dto.CommentDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.CommentAnswer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.security.util.SecurityHelper;
import com.javamentor.qa.platform.service.abstracts.dto.AnswerDtoService;
import com.javamentor.qa.platform.service.abstracts.dto.QuestionDtoService;
import com.javamentor.qa.platform.service.abstracts.dto.UserDtoService;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.abstracts.model.CommentAnswerService;
import com.javamentor.qa.platform.service.abstracts.model.CommentService;
import com.javamentor.qa.platform.webapp.converters.CommentConverter;
import com.javamentor.qa.platform.models.dto.CommentAnswerDto;
import com.javamentor.qa.platform.models.dto.CommentDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.service.abstracts.dto.CommentDtoService;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("/api/question")
@Api(value = "AnswerApi")
public class AnswerController {
    private final AnswerService answerService;
    private final CommentAnswerService commentAnswerService;
    private final CommentConverter commentConverter;
    private final SecurityHelper securityHelper;
    private final CommentDtoService commentDtoService;
    private final QuestionService questionService;
    private final UserDtoService userDtoService;
    private final QuestionDtoService questionDtoService;
    private final AnswerDtoService answerDtoService;


    @Autowired
    public AnswerController(AnswerService answerService,
                            CommentAnswerService commentAnswerService,
                            CommentConverter commentConverter,
                            SecurityHelper securityHelper,
                            CommentDtoService commentDtoService,
                            QuestionService questionService,
                            UserDtoService userDtoService,
                            QuestionDtoService questionDtoService,
                            AnswerDtoService answerDtoService) {
        this.answerService = answerService;
        this.commentAnswerService = commentAnswerService;
        this.commentConverter = commentConverter;
        this.securityHelper = securityHelper;
        this.commentDtoService = commentDtoService;
        this.questionService = questionService;
        this.userDtoService = userDtoService;
        this.questionDtoService = questionDtoService;
        this.answerDtoService = answerDtoService;
    }

    @PostMapping("/{questionId}/answer/{answerId}/comment")
    @ApiOperation(value = "Add comment", notes = "This method Add comment to answer and return CommentDto")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Comment was added", response = CommentDto.class),
            @ApiResponse(code = 400, message = "Answer or question not found", response = String.class)
    })
    public ResponseEntity<?> addCommentToAnswer(
            @ApiParam(name = "AnswerId", value = "AnswerId. Type long", required = true, example = "1")
            @PathVariable Long answerId,
            @ApiParam(name = "QuestionId", value = "QuestionId. Type long", required = true, example = "1")
            @PathVariable Long questionId,
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

    @GetMapping("/{questionId}/answer/{answerId}/comments")
    @ApiOperation(value = "Return all Comments by answerID", notes = "Return all Comments by answerID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Return all Comments by answerID", response = CommentDto.class,  responseContainer = "List"),
            @ApiResponse(code = 400, message = "Answer not found", response = String.class)
    })

    public ResponseEntity<?> getCommentListByAnswerId(
            @ApiParam(name = "questionId", value = "questionId. Type Long", required = true, example = "0")
            @PathVariable Long questionId,
            @ApiParam(name = "answerId", value = "answerId. Type long", required = true, example = "1")
            @PathVariable Long answerId) {

        Optional<Question> question = questionService.getById(questionId);
        if (!question.isPresent()) {
            return ResponseEntity.badRequest().body("Question was not found");
        }

        Optional<Answer> answer = answerService.getById(answerId);
        if (!answer.isPresent()) {
            return ResponseEntity.badRequest().body("Answer not found");
        }

        List<CommentAnswerDto> commentAnswerDtoList = commentDtoService.getAllCommentsByAnswerId(answerId);

        return ResponseEntity.ok(commentAnswerDtoList);
    }

    /*@GetMapping("/{questionId}/isAnswer")
    @ApiOperation(value = "Checks if user answered the question",
            notes = "Provide an question ID, to check, if current user have already answered this question",
            response = Boolean.class)
    public ResponseEntity<Boolean> isUserAnsweredQuestion(@ApiParam(name = "questionId", value = "ID value, for the question you need to check", required = true)
                                                          @PathVariable Long questionId) {



        return ResponseEntity.ok(false); // delete this
    }*/

    @GetMapping("/{questionId}/isAswerVoted/")
    @ApiOperation(value = "Checks if user vote up answer to the question",
            notes = "Provide an question ID, to check, if current user have already voted up ANY answer to this question",
            response = Boolean.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "True, if user voted; False, if not", response = Boolean.class),
            @ApiResponse(code = 400, message = "User/question have not found", response = String.class),
    })
    public ResponseEntity<?> isAnyAnswerVotedByCurrentUser(@ApiParam(name = "questionId", value = "ID value, for the question, the answer to which needs to be check", required = true)
                                                     @PathVariable Long questionId) {

        Optional<QuestionDto> questionDto = questionDtoService.getQuestionDtoById(questionId);

        if (!questionDto.isPresent()) {
            return ResponseEntity.badRequest().body("Question not found");
        }
        // TODO: get userId, from principal/security (username == email)

        /*Optional<UserDtoService> userDto = userDtoService.getUserDtoById(userId);

        if (!userDto.isPresent()) {
            return ResponseEntity.badRequest().body("User not found");
        }*/

        // TODO: 1) get all answers on this question(if present)  2) get all votes on answers(if present) 3) find crossing on user_id AND answer_id.
        // OPTION #2 :: add question_id to votes_on_answers
        // >>>>OPTION #3 :: add *this* logic to DaoDto, cuz with select/inner join its easier. And task, by itself, is regular, af

        return ResponseEntity.ok(false); // delete this
    }


}
