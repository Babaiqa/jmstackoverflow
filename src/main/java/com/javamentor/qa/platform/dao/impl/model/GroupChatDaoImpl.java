package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.GroupChatDao;
import com.javamentor.qa.platform.models.entity.chat.GroupChat;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class GroupChatDaoImpl extends ReadWriteDaoImpl<GroupChat, Long> implements GroupChatDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean existsGroupChatByTitle(String title) {
        return (entityManager.unwrap(Session.class)
                .createQuery("select gc.chat.title from GroupChat gc where gc.chat.title = :title")
                .setParameter("title", title)
                .unwrap(Query.class)
                .getResultList()).isEmpty();
    }
}
