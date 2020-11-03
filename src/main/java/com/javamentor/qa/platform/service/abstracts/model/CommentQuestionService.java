package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.dto.CommentDto;
import com.javamentor.qa.platform.models.entity.question.CommentQuestion;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.user.User;

import java.util.Optional;

public interface CommentQuestionService extends ReadWriteService<CommentQuestion, Long> {

     Optional<CommentDto> addCommentToQuestion(String commentText, Question question, User user);
}
