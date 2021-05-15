package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.QuestionDtoDao;
import com.javamentor.qa.platform.dao.impl.dto.transformers.QuestionResultTransformer;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class QuestionDtoDaoImpl implements QuestionDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<QuestionDto> getQuestionDtoById(Long id) {

        return (Optional<QuestionDto>) entityManager.unwrap(Session.class)
                .createQuery("select question.id as question_id, " +
                        " question.title as question_title," +
                        "u.fullName as question_authorName," +
                        " u.id as question_authorId, " +
                        "u.imageLink as question_authorImage," +
                        "question.description as question_description," +
                        "(select count(qv) from QuestionViewed qv where qv.question.id = :id) as question_viewCount," +
                        "(select count(a.question.id) from Answer a where a.question.id=:id) as question_countAnswer," +
                        "(select coalesce(sum(v.vote), 0) from VoteQuestion v where v.question.id=:id) as question_countValuable," +
                        "question.persistDateTime as question_persistDateTime," +
                        "question.lastUpdateDateTime as question_lastUpdateDateTime, " +
                        " tag.id as tag_id,tag.name as tag_name, tag.description as tag_description " +
                        "from Question question  " +
                        "INNER JOIN  question.user u" +
                        "  join question.tags tag"
                        + " where question.id=:id")
                .setParameter("id", id)
                .unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(new QuestionResultTransformer())
                .uniqueResultOptional();

    }

    @Override
    public List<Long> getPaginationQuestionIdsWithoutAnswerOrderByNew(int page, int size) {
        return   (List<Long>) entityManager
                .createQuery("select q.id from Question q left outer join Answer a on (q.id = a.question.id) where a.question.id is null order by q.persistDateTime desc")
                .setFirstResult(page * size - size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public List<Long> getPaginationQuestionIdsWithoutAnswerWithIgnoredTags(int page, int size, long id) {
        return  (List<Long>) entityManager.createQuery(
                "select q.id " +
                        "from Question q " +
                        "join  q.tags tag " +
                        "left outer join Answer a on (q.id = a.question.id) " +
                        "join IgnoredTag ignoredTag on tag.id=ignoredTag.ignoredTag.id " +
                        "inner join User user on user.id=ignoredTag.user.id " +
                        "where  user.id in :id and a.question.id is null ")
                .setParameter("id", id)
                .setFirstResult(page * size - size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public List<Long> getPaginationQuestionIdsWithoutAnswer(int page, int size) {
        return entityManager
                .createQuery("select q.id from Question q left outer join Answer a on (q.id = a.question.id) where a.question.id is null")
                .setFirstResult(page * size - size)
                .setMaxResults(size)
                .getResultList();
    }


    @Override
    public List<Long> getPaginationQuestionIdsWithoutAnswerWithTrackedTags(int page, int size, long id) {
        return  (List<Long>) entityManager.createQuery(
                "select q.id " +
                        "from Question q " +
                        "join  q.tags tag " +
                        "left outer join Answer a on (q.id = a.question.id) " +
                        "join TrackedTag trackedTag on tag.id=trackedTag.trackedTag.id " +
                        "inner join User user on user.id=trackedTag.user.id " +
                        "where  user.id in :id and a.question.id is null ")
                .setParameter("id", id)
                .setFirstResult(page * size - size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public  List<Long> getPaginationQuestionIdsOrderByNew(int page, int size){
        return   (List<Long>) entityManager
                .createQuery("select q.id from Question q left outer join Answer a on (q.id = a.question.id) order by q.persistDateTime desc")
                .setFirstResult(page * size - size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public  List<Long> getPaginationQuestionIdsWithoutAnswerOrderByVotes(int page, int size){
        return   (List<Long>) entityManager
                .createQuery("select q.id from Question q left outer join Answer a on (q.id = a.question.id) " +
                        " full join VoteQuestion vq on(q.id = vq.question.id) where a.question.id is null or a.isHelpful = false group by q.id order by coalesce(sum(vq.vote), 0) desc ")
                .setFirstResult(page * size - size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public List<Long> getPaginationQuestionIdsPopularWithTrackedTags(int page, int size, long id) {
        return (List<Long>) entityManager.createQuery(
                "select q.id " +
                        "from Question q " +
                        "join  q.tags tag " +
                        "join TrackedTag trackedTag on tag.id=trackedTag.trackedTag.id " +
                        "inner join User user on user.id=trackedTag.user.id " +
                        "where  user.id in :id")
                .setParameter("id", id)
                .setFirstResult(page * size - size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public List<Long> getPaginationQuestionIdsPopularWithIgnoredTags(int page, int size, long id) {
        return (List<Long>) entityManager.createQuery(
                "select q.id " +
                        "from Question q " +
                        "join  q.tags tag " +
                        "join IgnoredTag ignoredTag on tag.id=ignoredTag.ignoredTag.id " +
                        "inner join User user on user.id=ignoredTag.user.id " +
                        "where  user.id in :id")
                .setParameter("id", id)
                .setFirstResult(page * size - size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public List<Long> getPaginationQuestionIdsWithFollowAndWithoutIgnoreTags(int page, int size, long id) {
        return entityManager.createQuery(
                "select distinct q.id " +
                        "from Question q " +
                        "join q.tags tag " +
                        "join TrackedTag trackedTag on tag.id = trackedTag.trackedTag.id " +
                        "where trackedTag.user.id in :id and q.id not in (" +
                            "select distinct q_ignor.id from Question q_ignor " +
                            "join q_ignor.tags tag_ignor " +
                            "join IgnoredTag ignoredTag on tag_ignor.id = ignoredTag.ignoredTag.id " +
                            "where ignoredTag.user.id in :id)", Long.class)
                .setParameter("id", id)
                .setFirstResult(page * size - size)
                .setMaxResults(size)
                .getResultList();
    }
}