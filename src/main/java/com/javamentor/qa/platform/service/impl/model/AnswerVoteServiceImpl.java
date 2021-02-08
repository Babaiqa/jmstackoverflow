package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.VoteAnswerDao;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import com.javamentor.qa.platform.service.abstracts.model.AnswerVoteService;
import org.springframework.stereotype.Service;

@Service
public class AnswerVoteServiceImpl extends ReadWriteServiceImpl<VoteAnswer, Long> implements AnswerVoteService {


    public AnswerVoteServiceImpl(VoteAnswerDao voteAnswerDao) {
        super(voteAnswerDao);
    }
}
