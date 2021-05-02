package com.javamentor.qa.platform.dao.impl.dto.pagination.questions;

import com.javamentor.qa.platform.dao.abstracts.dto.pagination.PaginationDao;
import com.javamentor.qa.platform.dao.impl.dto.transformers.QuestionForPricipalResultTransformer;
import com.javamentor.qa.platform.models.dto.QuestionDtoPrincipal;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository(value = "PaginationAllQuestionsPrincipalUserDao")
public class PaginationAllQuestionsPrincipalUserDaoImpl implements PaginationDao<QuestionDtoPrincipal> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<QuestionDtoPrincipal> getItems(Map<String, Object> parameters) {

        int page = (int) parameters.get("page");
        int size = (int)parameters.get("size");
        long userId = (long) parameters.get("userId");

        return (List<QuestionDtoPrincipal>) entityManager.unwrap(Session.class)
                .createQuery("SELECT q.id as question_id, u.id as user_id, " +
                        "q.title as question_title, q.persistDateTime as persist_date, " +
                        "q.description as question_description, (select coalesce(sum(v.vote), 0) from VoteQuestion v where v.question.id=q.id) as question_countValuable " +
                        "FROM Question as q " +
                        "INNER JOIN q.user as u " +
                        "WHERE u.id = :userId order by persist_date desc ")
                .unwrap(Query.class)
                .setParameter("userId", userId)
                .setResultTransformer(new QuestionForPricipalResultTransformer())
                .setFirstResult(page * size - size)
                .setMaxResults(size)
                .getResultList();

    }

    @Override
    public int getCount(Map<String, Object> parameters) {
        long userId = (long) parameters.get("userId");
        return (int)(long) entityManager.createQuery("select count (q.id)" +
                " from  Question q inner join q.user as u" +
                " where u.id = :userId")
                .setParameter("userId", userId)
                .getSingleResult();
    }
}
