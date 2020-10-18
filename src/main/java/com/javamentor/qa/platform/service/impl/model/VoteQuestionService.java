package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.impl.model.VoteQuestionDaoImpl;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import org.springframework.stereotype.Service;

@Service
public class VoteQuestionService extends ReadWriteServiceImpl<VoteQuestion, Long>{
    public VoteQuestionService(VoteQuestionDaoImpl voteQuestionDaoImpl) {
        super(voteQuestionDaoImpl);
    }
}
