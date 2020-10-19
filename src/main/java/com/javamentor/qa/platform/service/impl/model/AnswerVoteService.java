package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.impl.model.AnswerVoteDaoImpl;
import com.javamentor.qa.platform.models.entity.question.answer.AnswerVote;
import org.springframework.stereotype.Service;

@Service
public class AnswerVoteService extends ReadWriteServiceImpl<AnswerVote, Long> {
    public AnswerVoteService(AnswerVoteDaoImpl answerVoteDaoImpl) {
        super(answerVoteDaoImpl);
    }
}
