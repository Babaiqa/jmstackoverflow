package com.javamentor.qa.platform.dao.impl.dto.pagination.questions;

import com.javamentor.qa.platform.dao.abstracts.dto.pagination.PaginationDao;
import com.javamentor.qa.platform.dao.impl.dto.transformers.QuestionResultTransformer;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository(value = "paginationQuestionWithoutTags")
@SuppressWarnings(value = "unchecked")
public class PaginationQuestionWithoutTagsDaoImpl implements PaginationDao<QuestionDto> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<QuestionDto> getItems(Map<String, Object> parameters) {

        int page = (int) parameters.get("page");
        int size = (int) parameters.get("size");
        List<Long> tagsIds = (List<Long>)parameters.get("tagsIds");

        List<Long> questionIds = (List<Long>)em.createQuery("select distinct question.id from Question as question " +
                "where (select count (tag_1) from question.tags as tag_1) = " +
                "(select count (tag_2) from question.tags as tag_2 where tag_2.id not in :tagIds)")
                .setParameter("tagIds", tagsIds)
                .setFirstResult(page * size - size)
                .setMaxResults(size)
                .getResultList();

        return em.unwrap(Session.class)
                .createQuery("select question.id as question_id, " +
                        " question.title as question_title," +
                        "u.fullName as question_authorName," +
                        " u.id as question_authorId, " +
                        "u.imageLink as question_authorImage," +
                        "question.description as question_description," +
                        "COUNT (qv.question.id) AS question_viewCount, " +
                        "(select count(a.id) from Answer a where a.question.id=question.id and a.isDeletedByModerator = false) as question_countAnswer," +
                        "coalesce((select sum(v.vote) from VoteQuestion v where v.question.id = question.id), 0) as question_countValuable," +
                        "question.persistDateTime as question_persistDateTime," +
                        "question.lastUpdateDateTime as question_lastUpdateDateTime, " +
                        " tag.id as tag_id,tag.name as tag_name, tag.description as tag_description " +
                        "from Question question  " +
                        "left join QuestionViewed qv on question.id = qv.question.id " +
                        "INNER JOIN  question.user u" +
                        "  join question.tags tag" +
                        " where question_id IN :ids ORDER BY question_viewCount DESC")
                .setParameter("ids", questionIds)
                .unwrap(Query.class)
                .setResultTransformer(new QuestionResultTransformer())
                .getResultList();
    }

    @Override
    public int getCount(Map<String, Object> parameters) {
        List<Long> tagsIds = (List<Long>)parameters.get("tagsIds");
        return (int)(long) em.createQuery("select count (distinct question.id) from Question as question " +
                "where (select count (tag_1) from question.tags as tag_1) = " +
                "(select count (tag_2) from question.tags as tag_2 where tag_2.id not in :tagIds)")
                .setParameter("tagIds", tagsIds)
                .getSingleResult();
    }
}
