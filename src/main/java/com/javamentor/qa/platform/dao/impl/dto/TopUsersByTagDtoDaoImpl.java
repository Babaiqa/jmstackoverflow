package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TopUsersByTagDtoDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.UserDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class TopUsersByTagDtoDaoImpl implements TopUsersByTagDtoDao {
    @PersistenceContext
    private final EntityManager entityManager;

    public TopUsersByTagDtoDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<UserDto> getTopUserByTagIdDto(Long id) {
        TypedQuery<UserDto> query = entityManager.createQuery(
                "SELECT new com.javamentor.qa.platform.models.dto.UserDto(v.user.id, v.user.email, v.user.fullName, v.user.imageLink, v.vote)" +
                        "FROM VoteAnswer v INNER JOIN QuestionHasTag q ON q.question.id = v.answer.question.id WHERE q.tag.id=:id ORDER BY v.vote", UserDto.class)
                .setParameter("id", id);
        return SingleResultUtil.getSingleResultOrNull(query);
    }
}
