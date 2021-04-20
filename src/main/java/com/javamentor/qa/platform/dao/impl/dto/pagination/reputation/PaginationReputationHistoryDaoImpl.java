package com.javamentor.qa.platform.dao.impl.dto.pagination.reputation;

import com.javamentor.qa.platform.dao.abstracts.dto.pagination.PaginationDao;
import com.javamentor.qa.platform.dao.impl.dto.transformers.ReputationHistoryDtoListTransformer;
import com.javamentor.qa.platform.models.dto.ReputationHistoryDtoList;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.query.Query;
import java.util.List;
import java.util.Map;

@Repository(value = "paginationReputationHistory")
@SuppressWarnings(value = "unchecked")
public class PaginationReputationHistoryDaoImpl implements PaginationDao<ReputationHistoryDtoList> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<ReputationHistoryDtoList> getItems(Map<String, Object> parameters) {
        return (List<ReputationHistoryDtoList>) em.unwrap(Session.class)
                .createQuery("select reputation.id as reputation_id," +
                        "reputation.count as reputation_count, " +
                        "reputation.type as reputation_type, " +
                        "reputation.persistDate as reputation_persistDate " +
                        "from Reputation reputation " +
                        "where reputation.id=:id")
                .setParameter("id", parameters.get("reputationId"))
                .unwrap(Query.class)
                .setResultTransformer(new ReputationHistoryDtoListTransformer())
                .getResultList();
    }

    @Override
    public int getCount(Map<String, Object> parameters) {
        return (int)(long) em.createQuery("select count(r) from Reputation r").getSingleResult();
    }
}
