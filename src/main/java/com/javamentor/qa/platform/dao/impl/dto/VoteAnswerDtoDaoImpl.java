package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.VoteAnswerDtoDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.VoteAnswerDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class VoteAnswerDtoDaoImpl implements VoteAnswerDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<VoteAnswerDto> getVoteByQuestionIdAndUserId(Long questionId, Long userId) {
        TypedQuery<VoteAnswerDto> query = entityManager.createQuery(
                "SELECT new com.javamentor.qa.platform.models.dto.VoteAnswerDto(v.id, v.user.id, v.answer.id, v.persistDateTime, v.vote)" +
                        "FROM VoteAnswer v INNER JOIN Answer a ON v.answer.id = a.id " +
                        "WHERE v.user.id =: userId AND a.question.id =: questionId", VoteAnswerDto.class)

                .setParameter("questionId", questionId)
                .setParameter("userId", userId);

        return SingleResultUtil.getSingleResultOrNull(query);
    }

    @Override
    public Optional<VoteAnswerDto> getVoteByAnswerIdAndUserId(Long answerId, Long userId) {
        TypedQuery<VoteAnswerDto> query = entityManager.createQuery(
                "SELECT new com.javamentor.qa.platform.models.dto.VoteAnswerDto(v.id, v.user.id, v.answer.id, v.persistDateTime, v.vote)" +
                        "FROM VoteAnswer v " +
                        "WHERE v.user.id =: userId AND v.answer.id =: answerId", VoteAnswerDto.class)

                .setParameter("answerId", answerId)
                .setParameter("userId", userId);

        return SingleResultUtil.getSingleResultOrNull(query);
    }
}
