package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.QuestionDtoDao;
import com.javamentor.qa.platform.dao.impl.dto.transformers.QuestionResultTransformer;
import com.javamentor.qa.platform.dao.impl.dto.transformers.QuestionResultTransformerTagOnly;
import com.javamentor.qa.platform.dao.impl.dto.transformers.QuestionResultTransformerWithoutTag;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
                        " question.viewCount as question_viewCount," +
                        "(select count(a.question.id) from Answer a where a.question.id=:id) as question_countAnswer," +
                        "(select count(v.question.id) from VoteQuestion v where v.question.id=:id) as question_countValuable," +
                        "question.persistDateTime as question_persistDateTime," +
                        "question.lastUpdateDateTime as question_lastUpdateDateTime, " +
                        " tag.id as tag_id,tag.name as tag_name " +
                        "from Question question  " +
                        "INNER JOIN  question.user u" +
                        "  join question.tags tag"
                        + " where question.id=:id")
                .setParameter("id", id)
                .unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(new QuestionResultTransformer())
                .uniqueResultOptional();
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public List<Question> getPagination(int page, int size) {

        return entityManager.createQuery("from Question ")
                .setFirstResult(page * size - size)
                .setMaxResults(size)
                .getResultList();
    }


    public List<QuestionDto> getQuestionDtoByTagIds(List<Long> ids) {

        return (List<QuestionDto>) entityManager.unwrap(Session.class)
                .createQuery("select question.id as question_id, " +
                        " question.title as question_title," +
                        "u.fullName as question_authorName," +
                        " u.id as question_authorId, " +
                        "u.imageLink as question_authorImage," +
                        "question.description as question_description," +
                        " question.viewCount as question_viewCount," +
                        "(select count(a.question.id) from Answer a where a.question.id=question_id) as question_countAnswer," +
                        "(select count(v.question.id) from VoteQuestion v where v.question.id=question_id) as question_countValuable," +
                        "question.persistDateTime as question_persistDateTime," +
                        "question.lastUpdateDateTime as question_lastUpdateDateTime, " +
                        " tag.id as tag_id,tag.name as tag_name " +
                        "from Question question  " +
                        "INNER JOIN  question.user u" +
                        "  join question.tags tag" +
                        " where question_id IN :ids order by question.viewCount desc")
                .setParameter("ids", ids)
                .unwrap(Query.class)
                .setResultTransformer(new QuestionResultTransformer())
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Long> getQuestionsNotAnsweredIDs(int page, int size) {
        return (List<Long>) entityManager
                .createQuery("select q.id from Question q left outer join Answer a on (q.id = a.question.id) where a.question.id is null")
                .setFirstResult(page * size - size)
                .setMaxResults(size)
                .getResultList();
    }

    public long getTotalCountQuestionNotAnswer(){
        return (long) entityManager.createQuery("select count (q.id) from Question q left outer join Answer a on (q.id = a.question.id) where a.question.id is null").getSingleResult();
    }

    @SuppressWarnings("unchecked")
    public List<QuestionDto> getQuestionDtoByIds(List<Long> ids) {
        return (List<QuestionDto>) entityManager.unwrap(Session.class)
                .createQuery("select question.id as question_id, " +
                        "question.title as question_title," +
                        "u.fullName as question_authorName," +
                        "u.id as question_authorId, " +
                        "u.imageLink as question_authorImage," +
                        "question.description as question_description," +
                        "question.viewCount as question_viewCount," +
                        "(select count(a.question.id) from Answer a where a.question.id=question_id) as question_countAnswer," +
                        "(select count(v.question.id) from VoteQuestion v where v.question.id=question_id) as question_countValuable," +
                        "question.persistDateTime as question_persistDateTime," +
                        "question.lastUpdateDateTime as question_lastUpdateDateTime, " +
                        "tag.id as tag_id,tag.name as tag_name " +
                        "from Question question  " +
                        "INNER JOIN  question.user u " +
                        "join question.tags tag " +
                        "where question_id IN :ids")
                .setParameter("ids", ids)
                .unwrap(Query.class)
                .setResultTransformer(new QuestionResultTransformer())
                .getResultList();
    }

    @Override
    public int getTotalResultCountQuestionDto() {
        long totalResultCount = (long) entityManager.createQuery("select count(*) from Question").getSingleResult();
        return (int) totalResultCount;
    }

    @Override
    public List<QuestionDto> getPaginationOrderedNew(int page, int size) {
        List<QuestionDto> questionDtoList = entityManager.unwrap(Session.class)
                .createQuery(
                        "select question.id as question_id, " +
                                "question.title as question_title, " +
                                "u.fullName as question_authorName, " +
                                "u.id as question_authorId, " +
                                "u.imageLink as question_authorImage, " +
                                "question.description as question_description, " +
                                "question.viewCount as question_viewCount, " +
                                "(select count(a.question.id) from Answer a where question_id=a.question.id) as question_countAnswer, " +
                                "(select count(v.question.id) from VoteQuestion v where question_id=v.question.id) as question_countValuable, " +
                                "question.persistDateTime as question_persistDateTime, " +
                                "question.lastUpdateDateTime as question_lastUpdateDateTime " +
                                "from Question question " +
                                "INNER JOIN question.user u " +
                                "order by question_lastUpdateDateTime desc "
                )
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(new QuestionResultTransformerWithoutTag())
                .getResultList();

        return questionDtoList;
    }

    @Override
    public List<QuestionDto> getQuestionTagsByQuestionIds(List<Long> ids) {
        List<QuestionDto> tagsByIds = entityManager.unwrap(Session.class)
                .createQuery(
                        "select question.id as question_id," +
                                "tag.id as tag_id," +
                                "tag.name as tag_name " +
                                "from Question question " +
                                "inner join question.user u " +
                                "join question.tags tag " +
                                "where question_id IN :ids"
                )
                .setParameter("ids", ids)
                .setResultTransformer(new QuestionResultTransformerTagOnly())
                .getResultList();
        return tagsByIds;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<QuestionDto> getQuestionBySearchValue(Map<String, String> data) {
        String query = "select question.id as question_id, " +
                "question.title as question_title," +
                "u.fullName as question_authorName," +
                "u.id as question_authorId, " +
                "u.imageLink as question_authorImage," +
                "question.description as question_description," +
                "question.viewCount as question_viewCount," +
                "(select count(a.question.id) from Answer a where a.question.id=question_id) as question_countAnswer," +
                "(select count(v.question.id) from VoteQuestion v where v.question.id=question_id) as question_countValuable," +
                "question.persistDateTime as question_persistDateTime," +
                "question.lastUpdateDateTime as question_lastUpdateDateTime, " +
                "tag.id as tag_id,tag.name as tag_name " +
                "from Question question  " +
                "INNER JOIN  question.user u " +
                "join question.tags tag " +
                "where u.id >= :userId " +
                "and question.viewCount >= :views " +
                "and (select count(a.question.id) from Answer a where a.question.id=question_id) >= :answers " +
                "and question.description like :exactly ";

        String[] tags = data.get("tags").split(" ");
        String tagsQuery = "and (";
        for (String tag : tags) {
            tagsQuery += "tag.name like '%" + tag + "%' or ";
        }
        query += tagsQuery.substring(0, tagsQuery.length() - 4) + ")";

        String[] words = data.get("textSearch").split(" ");
        String wordsQuery = " and (";
        for (String word: words) {
            wordsQuery += "question.description like '%" + word + "%' or ";
        }
        query += wordsQuery.substring(0, wordsQuery.length() - 4) + ")";

        return (List<QuestionDto>) entityManager.unwrap(Session.class)
                .createQuery(query)
                .setParameter("userId", data.get("author").isEmpty()? 0 : Long.parseLong(data.get("author")))
                .setParameter("views", data.get("views").isEmpty()? 0 : Integer.parseInt(data.get("views")))
                .setParameter("answers", data.get("answers").isEmpty()? 0 : Long.parseLong(data.get("answers")))
                .setParameter("exactly", data.get("exactSearch").isEmpty()? "%" : "%" + data.get("exactSearch") + "%")
                .unwrap(Query.class)
                .setResultTransformer(new QuestionResultTransformer())
                .getResultList();
    }
}