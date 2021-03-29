package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.VoteAnswerDao;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import com.javamentor.qa.platform.models.entity.user.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class VoteAnswerDaoImpl extends ReadWriteDaoImpl<VoteAnswer, Long> implements VoteAnswerDao {
    @PersistenceContext
    EntityManager entityManager;

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
                .setParameter("questionId", question.getId())
                .getSingleResult();
    }
}
