package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.http.ResponseEntity;

public interface VoteQuestionService extends ReadWriteService<VoteQuestion, Long> {

    boolean isUserAlreadyVoted(Question question, User user);

    ResponseEntity<String> answerUpVote(Question question, User user);

    ResponseEntity<String> answerDownVote(Question question, User user);
}
