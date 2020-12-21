package com.javamentor.qa.platform.dao.impl.pagination.user;

import com.javamentor.qa.platform.dao.abstracts.pagination.PaginationDao;
import com.javamentor.qa.platform.dao.impl.dto.transformers.UserDtoListTranformer;
import com.javamentor.qa.platform.models.dto.UserDtoList;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository(value = "paginationUserReputationByName")
@SuppressWarnings(value = "unchecked")
public class PaginationUserReputationByNameDaoImpl implements PaginationDao<UserDtoList> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<UserDtoList> getItems(Map<String, Object> parameters) {
        int page = (int) parameters.get("page");
        int size = (int) parameters.get("size");
        String name = (String) parameters.get("name");

        List<Long> usersIds = (List<Long>) em.createQuery(
                "select user.id from User user where user.fullName like :name")
                .setParameter("name", "%"+name+"%")
                .setFirstResult(page * size - size)
                .setMaxResults(size)
                .getResultList();

        return (List<UserDtoList>) em.unwrap(Session.class)
                .createQuery("select user.id as user_id, " +
                        "user.fullName as full_name, " +
                        "user.imageLink as link_image, " +
                        "(select coalesce(sum(ra.count), 0) from Reputation ra where ra.user.id = user_id) as reputation, " +
                        "tag.id as tag_id, tag.name as tag_name " +
                        "from User user " +
                        "left outer join Reputation r on user.id = r.user.id " +
                        "left join Question question on user.id=question.user.id " +
                        "join question.tags tag " +
                        "left join Answer answer on answer.question.id=question.id " +
                        "where question.user.id in (:ids) " +
                        "or answer.user.id in (:ids) " +
                        "order by user.reputationCount desc")
                .setParameter("ids", usersIds)
                .unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(new UserDtoListTranformer())
                .getResultList();
    }

    @Override
    public int getCount(Map<String, Object> parameters) {
        String name = (String) parameters.get("name");
        return (int)(long) em.createQuery(
                "select count(user) from User user where user.fullName like :name")
                .setParameter("name", "%"+name+"%")
                .getSingleResult();
    }
}
