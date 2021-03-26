package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.SingleChatDtoDao;
import com.javamentor.qa.platform.dao.impl.dto.transformers.SingleChatResultTransformer;
import com.javamentor.qa.platform.models.dto.SingleChatDto;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import org.hibernate.Session;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class SingleChatDtoDaoImpl implements SingleChatDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<SingleChatDto> findSingleChatDtoById(Long id) {
        return (Optional<SingleChatDto>) entityManager.unwrap(Session.class)
                .createQuery("SELECT new SingleChatDto(" +
                        "sc.id, " +
                        "sc.chat.id, " +
                        "sc.userOne.id, " +
                        "sc.useTwo.id) " +
                        "FROM SingleChat sc " +
                        "JOIN Chat c ON sc.chat.id = c.id " +
                        "WHERE sc.id = :id")
                .setParameter("id", id)
                .unwrap(Query.class)
                .uniqueResultOptional();
    }

    @Override
    public List<SingleChatDto> getAllSingleChatDto() {

        return (List<SingleChatDto>) entityManager.unwrap(Session.class)
                .createQuery("SELECT sc.id as id, " +
                        "sc.chat.id as chatId, " +
                        "sc.userOne.id as userOneId, " +
                        "sc.useTwo.id as userTwoId " +
                        "FROM SingleChat sc " +
                        "JOIN Chat c on sc.chat.id = c.id ")
                .unwrap(Query.class)
                .setResultTransformer(new SingleChatResultTransformer())
                .getResultList();
    }
}
