package com.javamentor.qa.platform.dao.impl.dto.pagination.chats;

import com.javamentor.qa.platform.dao.abstracts.dto.pagination.PaginationDao;
import com.javamentor.qa.platform.models.dto.MessageDto;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository(value = "paginationMessage")
public class PaginationMessageDaoImpl implements PaginationDao<MessageDto> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<MessageDto> getItems(Map<String, Object> parameters) {
        int page = (int) parameters.get("page");
        int size = (int) parameters.get("size");
        long chatId = (long) parameters.get("chatId");

        return (List<MessageDto>) entityManager.unwrap(Session.class)
                .createQuery("SELECT new com.javamentor.qa.platform.models.dto.MessageDto(" +
                        "ms.id," +
                        "ms.message," +
                        "ms.persistDate," +
                        "ms.lastRedactionDate," +
                        "ms.userSender.id," +
                        "ms.chat.id )" +
                        "from Message ms  where ms.chat.id =: chatId order by ms.persistDate desc")
                .unwrap(Query.class)
                .setParameter("chatId", chatId)
                .getResultList();
    }


    @Override
    public int getCount(Map<String, Object> parameters) {
        return (int) (long) entityManager.createQuery("select count(ms) from Message ms").getSingleResult();
    }
}
