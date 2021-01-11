package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.AnswerVoteDao;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.AnswerVote;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.AnswerVoteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnswerVoteServiceImpl extends ReadWriteServiceImpl<AnswerVote, Long> implements AnswerVoteService {

    private final AnswerVoteDao answerVoteDao;

    public AnswerVoteServiceImpl(AnswerVoteDao answerVoteDao) {
        super(answerVoteDao);
        this.answerVoteDao = answerVoteDao;
    }

    @Transactional
    @Override
    public AnswerVote vote(User user, Answer answer, int vote) {
        AnswerVote answerVote = new AnswerVote(user, answer, vote);

        answerVoteDao.persist(answerVote);

        return answerVote;
    }


}
