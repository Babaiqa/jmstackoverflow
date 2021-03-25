package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.VoteAnswerDao;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.VoteAnswerService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class VoteAnswerServiceImpl extends ReadWriteServiceImpl<VoteAnswer, Long> implements VoteAnswerService {

    public VoteAnswerServiceImpl(VoteAnswerDao voteAnswerDao) {
        super(voteAnswerDao);
    }

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public boolean isUserAlreadyVotedIsThisQuestion(Question question, User user, Answer answer) {

        return (Boolean) entityManager.createQuery(" SELECT " +
                " case when exists " +
                "(select vote  from VoteAnswer vote  where (vote.answer.id =: answerId AND vote.user.id =: userId ) " +
                "OR (vote.answer.question.id =:questionId AND vote.user.id =: userId))" +
                " then true " +
                " else  false " +
                "END from Answer")
                .setParameter("answerId", answer.getId())
                .setParameter("userId", user.getId())
                .setParameter("questionId",question.getId())
                .getSingleResult();
    }
}