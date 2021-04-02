package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.ChatDtoDao;
import com.javamentor.qa.platform.models.dto.ChatDto;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class ChatDtoDaoImpl implements ChatDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ChatDto> getAllChatsByUser(Long userID) {
        List<ChatDto> groupChats = (List<ChatDto>) entityManager.unwrap(Session.class)
                .createQuery("SELECT gc.chat.id as id, " +
                        "gc.chat.title as title, " +
                        "gc.chat.persistDate as persistDate, " +
                        "gc.chat.chatType as chatType " +
                        "FROM GroupChat gc " +
                        "JOIN gc.users u ON u.id = :id")
                .setParameter("id", userID)
                .unwrap(Query.class)
                .getResultList();
        List<ChatDto> singleChat = (List<ChatDto>) entityManager.unwrap(Session.class)
                .createQuery("SELECT sc.chat.id as id, " +
                        "sc.chat.title as title, " +
                        "sc.chat.persistDate as persistDate, " +
                        "sc.chat.chatType as chatType " +
                        "FROM SingleChat sc " +
                        "WHERE sc.userOne.id = :id OR sc.useTwo.id = :id")
                .setParameter("id", userID)
                .unwrap(Query.class)
                .getResultList();
        return Stream.concat(singleChat.stream(), groupChats.stream())
                .collect(Collectors.toList());
    }
}
