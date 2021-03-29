package com.javamentor.qa.platform.service.abstracts.model;


import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import com.javamentor.qa.platform.models.entity.user.User;

import java.util.Optional;


public interface VoteAnswerService extends ReadWriteService<VoteAnswer, Long> {

    boolean isUserAlreadyVoted(Answer answer, User user);

    boolean isUserAlreadyVotedIsThisQuestion(Question question, User user, Answer answer);

    void markHelpful(Optional<Question> question, Optional<Answer> answer, boolean isHelpful);
}
