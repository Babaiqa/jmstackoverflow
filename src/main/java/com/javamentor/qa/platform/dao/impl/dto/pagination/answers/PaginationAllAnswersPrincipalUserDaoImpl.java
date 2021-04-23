package com.javamentor.qa.platform.dao.impl.dto.pagination.answers;

import com.javamentor.qa.platform.dao.abstracts.dto.pagination.PaginationDao;
import com.javamentor.qa.platform.dao.impl.dto.transformers.AnswerResultTransformer;
import com.javamentor.qa.platform.dao.impl.dto.transformers.QuestionResultTransformer;
import com.javamentor.qa.platform.models.dto.AnswerDto;
import com.javamentor.qa.platform.models.dto.MessageDto;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository(value = "PaginationAllAnswersPrincipalUserDao")
public class PaginationAllAnswersPrincipalUserDaoImpl implements PaginationDao<AnswerDto> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AnswerDto> getItems(Map<String, Object> parameters) {

        int page = (int)parameters.get("page");
        int size = (int)parameters.get("size");
        long userId = (long) parameters.get("userId");


        return (List<AnswerDto>) entityManager.unwrap(Session.class)
                .createQuery("SELECT a.id as answer_id, u.id as user_id, q.id as question_id, " +
                        "a.htmlBody as html_body, a.persistDateTime as persist_date, a.isHelpful as is_helpful, a.dateAcceptTime as date_accept_time, " +
                        "(select coalesce(sum(v.vote), 0) from VoteAnswer v where v.answer.id=a.id) as answer_countValuable, " +
                        "u.imageLink as image_link, u.fullName as fullName " +
                        "FROM Answer as a " +
                        "INNER JOIN a.user as u " +
                        "JOIN a.question as q " +
                        "WHERE u.id = :userId order by answer_countValuable desc ")
                .unwrap(Query.class)
                .setParameter("userId", userId)
                .setResultTransformer(new AnswerResultTransformer())
                .setFirstResult(page * size - size)
                .setMaxResults(size)

                .getResultList();
    }

    @Override
    public int getCount(Map<String, Object> parameters) {
        long userId = (long) parameters.get("userId");
        return (int)(long) entityManager.createQuery("select count (a.id)" +
                " from Answer a inner join a.user as u" +
                " where u.id = :userId")
                .setParameter("userId", userId)
                .getSingleResult();
    }
}
