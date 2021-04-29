package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.VoteAnswerDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class VoteAnswerDaoImpl extends ReadWriteDaoImpl<VoteAnswer, Long> implements VoteAnswerDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public boolean isUserAlreadyVotedIsThisQuestion(Question question, User user, Answer answer) {

        return (Boolean) entityManager.createQuery(" select " +
                " case when exists " +
                "(select vote from VoteAnswer vote where (vote.answer.id =: answerId and vote.user.id =: userId) " +
                "OR (vote.answer.question.id =:questionId and vote.user.id =: userId))" +
                " then true " +
                " else false " +
                "end from Answer")
                .setParameter("answerId", answer.getId())
                .setParameter("userId", user.getId())
                .setParameter("questionId", question.getId())
                .getSingleResult();
    }

    @Override
    public boolean isAuthorOfQuestion(Long questionId, Long userId) {

        return (Boolean) entityManager.createQuery("select" +
                " case when exists " +
                "(select question from Question question where (question.id =: questionId and question.user.id =: userId))" +
                " then true " +
                " else false " +
                "end from Question")
                .setParameter("questionId", questionId)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    @Override
    public Optional<VoteAnswer> getVoteByAnswerIdAndUserId(Long answerId, Long userId) {
        TypedQuery<VoteAnswer> query = entityManager.createQuery(
                "FROM VoteAnswer WHERE user.id =: userId AND answer.id =: answerId", VoteAnswer.class)
                .setParameter("userId", userId)
                .setParameter("answerId", answerId);
        return SingleResultUtil.getSingleResultOrNull(query);
    }
}
