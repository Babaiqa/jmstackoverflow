package com.javamentor.qa.platform.dao.impl.dto.pagination.questions;

import com.javamentor.qa.platform.dao.abstracts.dto.pagination.PaginationDao;
import com.javamentor.qa.platform.dao.impl.dto.transformers.QuestionResultTransformer;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository(value = "paginationQuestionByPopular")
@SuppressWarnings(value = "unchecked")
public class PaginationQuestionByPopularDaoImpl implements PaginationDao<QuestionDto> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<QuestionDto> getItems(Map<String, Object> parameters) {
        int page = (int) parameters.get("page");
        int size = (int) parameters.get("size");
        long days = (long) parameters.get("days");


        List<Long> questionIds = (List<Long>) em.unwrap(Session.class)
                .createQuery("SELECT question.id, count(qv.question.id)" +
                        "FROM Question question " +
                        "left join QuestionViewed qv on question.id = qv.question.id " +
                        "WHERE question.persistDateTime BETWEEN :startDate AND :endDate order by qv.question.id")
                .setFirstResult(page * size - size)
                .setParameter("startDate", LocalDateTime.now().minusDays(days))
                .setParameter("endDate", LocalDateTime.now())
                .unwrap(org.hibernate.query.Query.class)
                .setMaxResults(size)
                .getResultList();

        return (List<QuestionDto>) em.unwrap(Session.class)
                .createQuery("SELECT question.id AS question_id, " +
                        " question.title AS question_title," +
                        "u.fullName AS question_authorName," +
                        "u.id AS question_authorId, " +
                        "u.imageLink AS question_authorImage," +
                        "question.description AS question_description, " +
                        "COUNT (qv.question.id) AS question_viewCount, " +
                        "(SELECT COUNT (a.id) FROM Answer a WHERE a.question.id=question.id and a.isDeletedByModerator = false) AS question_countAnswer," +
                        "coalesce((select sum(v.vote) from VoteQuestion v where v.question.id = question.id), 0) AS question_countValuable," +
                        "question.persistDateTime AS question_persistDateTime, " +
                        "question.lastUpdateDateTime AS question_lastUpdateDateTime, " +
                        "tag.id AS tag_id,tag.name AS tag_name, tag.description as tag_description " +
                        "FROM Question question " +
                        "left join QuestionViewed qv on question.id = qv.question.id " +
                        "INNER JOIN  question.user u " +
                        "JOIN question.tags tag " +
                        "WHERE question.id IN :ids ORDER BY question_viewCount DESC")
                .setParameter("ids", questionIds)
                .unwrap(Query.class)
                .setResultTransformer(new QuestionResultTransformer())
                .getResultList();
    }

    @Override
    public int getCount(Map<String, Object> parameters) {
        return (int) (long) em.createQuery("select count(q) from Question q").getSingleResult();
    }

}
