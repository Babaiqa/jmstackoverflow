package com.javamentor.qa.platform.service.abstracts.model;


import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface VoteAnswerService extends ReadWriteService<VoteAnswer, Long> {

    boolean isUserAlreadyVotedIsThisQuestion(Question question, User user, Answer answer);

    void markHelpful(Question question, User user, Answer answer, boolean isHelpful);

    ResponseEntity<String> upVoteIfAlreadyVoted(Optional<Question> question, User user, Optional<Answer> answer);
//    ResponseEntity<String> upVoteIfAlreadyVoted(Question question, User user, Answer answer);
}
