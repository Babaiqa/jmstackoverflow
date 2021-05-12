package com.javamentor.qa.platform.dao.impl.dto.pagination.questions;

import com.javamentor.qa.platform.dao.abstracts.dto.pagination.PaginationDao;
import com.javamentor.qa.platform.dao.impl.dto.transformers.QuestionResultTransformer;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.webapp.converters.QuestionConverter;
import com.javamentor.qa.platform.webapp.converters.TagMapper;
import org.apache.poi.ss.formula.functions.T;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Repository(value = "paginationQuestion")
@SuppressWarnings(value = "unchecked")
public class PaginationQuestionDaoImpl implements PaginationDao<QuestionDto> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<QuestionDto> getItems(Map<String, Object> parameters) {

        return  (List<QuestionDto>) em.unwrap(Session.class)
                .createQuery("select question.id as question_id, " +
                        "question.title as question_title," +
                        "u.fullName as question_authorName," +
                        "u.id as question_authorId, " +
                        "u.imageLink as question_authorImage," +
                        "question.description as question_description," +
                        "(SELECT COUNT (q.id) FROM QuestionViewed q WHERE q.question.id = question.id) AS question_viewCount," +
                        "(select count(a.id) from Answer a where a.question.id = question.id and a.isDeletedByModerator = false) as question_countAnswer," +
                        "coalesce((select sum(v.vote) from VoteQuestion v where v.question.id = question.id), 0) as question_countValuable," +
                        "question.persistDateTime as question_persistDateTime," +
                        "question.lastUpdateDateTime as question_lastUpdateDateTime, " +
                        "tag.id as tag_id,tag.name as tag_name, tag.description as tag_description " +
                        "from Question question  " +
                        "inner join question.user u " +
                        "join question.tags tag WHERE question.id IN :ids")
                 .setParameter("ids", parameters.get("questionIds"))
                .unwrap(Query.class)
                .setResultTransformer(new QuestionResultTransformer())
                .getResultList();
    }



    @Override
    public int getCount(Map<String, Object> parameters) {
        return (int)(long) em.createQuery("select count(q) from Question q").getSingleResult();
    }
}
