package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TopUsersByTagDtoDao;
import com.javamentor.qa.platform.dao.impl.dto.transformers.QuestionResultTransformer;
import com.javamentor.qa.platform.dao.impl.dto.transformers.UserDtoListTranformer;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.dto.UserDto;
import org.hibernate.Session;
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

        return (Optional<UserDto>) entityManager.unwrap(Session.class)
                .createQuery("SELECT v.user.id as user_id," +
                        "v.user.email as user_email," +
                        "v.user.fullName as user_fullName," +
                        "v.user.imageLink as user_image " +
                        "FROM VoteAnswer v " +
                        "INNER JOIN question_has_tag t ON t.question_id = v.answer.question.id " +
                        "WHERE t.tag_id=:id" +
                        " ORDER BY SUM(v.vote)")
                .setParameter("id", id)
                .unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(new UserDtoListTranformer())
                .uniqueResultOptional();

    }
}
