package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.VoteQuestionDtoDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.VoteQuestionDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class VoteQuestionDtoDaoImpl implements VoteQuestionDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<VoteQuestionDto> getVoteByQuestionIdAndUserId(Long questionId, Long userId) {
        TypedQuery<VoteQuestionDto> query = entityManager.createQuery(
                "SELECT new com.javamentor.qa.platform.models.dto.VoteQuestionDto(v.id, v.user.id, v.question.id, v.localDateTime, v.vote)" +
                        "FROM VoteQuestion v " +
                        "WHERE v.user.id =: userId AND v.question.id =: questionId", VoteQuestionDto.class)

                .setParameter("questionId", questionId)
                .setParameter("userId", userId);

        return SingleResultUtil.getSingleResultOrNull(query);
    }
}
