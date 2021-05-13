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

@Repository(value = "paginationWithTrackedWithoutIgnoredTag")
@SuppressWarnings(value = "unchecked")
public class PaginationQuestionWithTrackedWithoutIgnoredTagImpl implements PaginationDao<QuestionDto> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<QuestionDto> getItems(Map<String, Object> parameters) {

        List<Long> questionIds = (List<Long>) parameters.get("ids");

        return (List<QuestionDto>) em.unwrap(Session.class)
                .createQuery("select question.id as question_id, " +
                        " question.title as question_title," +
                        "u.fullName as question_authorName," +
                        " u.id as question_authorId, " +
                        "u.imageLink as question_authorImage," +
                        "question.description as question_description," +
                        " question.viewCount as question_viewCount," +
                        "(select count(a.id) from Answer a where a.question.id=question.id and a.isDeletedByModerator = false) as question_countAnswer," +
                        "coalesce((select sum(v.vote) from VoteQuestion v where v.question.id = question.id), 0) as question_countValuable," +
                        "question.persistDateTime as question_persistDateTime," +
                        "question.lastUpdateDateTime as question_lastUpdateDateTime, " +
                        " tag.id as tag_id,tag.name as tag_name, tag.description as tag_description " +
                        "from Question question  " +
                        "INNER JOIN  question.user u" +
                        "  join question.tags tag" +
                        " where question_id IN :ids")
                .setParameter("ids", questionIds)
                .unwrap(Query.class)
                .setResultTransformer(new QuestionResultTransformer())
                .getResultList();
    }

    @Override
    public int getCount(Map<String, Object> parameters) {
        long id = (long) parameters.get("id");
        return (int)(long)em.createQuery(
                "select count(distinct q.id) " +
                        "from Question q " +
                        "join q.tags tag " +
                        "join TrackedTag trackedTag on tag.id = trackedTag.trackedTag.id " +
                        "where trackedTag.user.id in :id and q.id not in (" +
                        "select distinct q_ignor.id from Question q_ignor " +
                        "join q_ignor.tags tag_ignor " +
                        "join IgnoredTag ignoredTag on tag_ignor.id = ignoredTag.ignoredTag.id " +
                        "where ignoredTag.user.id in :id)")
                .setParameter("id", id)
                .getSingleResult();
    }
}
