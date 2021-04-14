package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.MessageDtoDao;
import com.javamentor.qa.platform.models.dto.MessageDto;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MessageDtoDaoImpl implements MessageDtoDao {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<MessageDto> getAllMessageByChatIdDto(Long chatId) {

        return (List<MessageDto>) entityManager.unwrap(Session.class)
                .createQuery("SELECT new com.javamentor.qa.platform.models.dto.MessageDto(" +
                        "ms.id ," +
                        "ms.message, " +
                        "ms.persistDate, " +
                        "ms.lastRedactionDate," +
                        "ms.userSender.id, " +
                        "ms.chat.id) "+
                        "from Message  ms where chat.id =: chatId")
                .setParameter("chatId", chatId)
                .unwrap(Query.class)
                .getResultList();
    }
}
