package com.javamentor.qa.platform.dao.impl.dto.pagination.chats;

import com.javamentor.qa.platform.dao.abstracts.dto.pagination.PaginationDao;
import com.javamentor.qa.platform.dao.impl.dto.transformers.SingleChatResultTransformer;
import com.javamentor.qa.platform.models.dto.SingleChatDto;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository(value = "paginationSingleChat")
public class PaginationSingleChatDaoImpl implements PaginationDao<SingleChatDto> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<SingleChatDto> getItems(Map<String, Object> parameters) {
        int page = (int)parameters.get("page");
        int size = (int)parameters.get("size");

        return (List<SingleChatDto>) entityManager.unwrap(Session.class)
                .createQuery("SELECT sc.id as id, " +
                        "sc.chat.id as chatId, " +
                        "sc.chat.title as title, " +
                        "sc.userOne.id as userOneId, " +
                        "sc.useTwo.id as userTwoId " +
                        "FROM SingleChat sc " +
                        "JOIN Chat c on sc.chat.id = c.id ")
                .unwrap(Query.class)
                .setFirstResult(page * size - size)
                .setMaxResults(size)
                .setResultTransformer(new SingleChatResultTransformer())
                .getResultList();
    }

    @Override
    public int getCount(Map<String, Object> parameters) {
        return (int)(long) entityManager.createQuery("select count(sc) from SingleChat sc").getSingleResult();
    }
}
