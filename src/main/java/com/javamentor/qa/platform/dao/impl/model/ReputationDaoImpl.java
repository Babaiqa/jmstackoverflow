package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.ReputationDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class ReputationDaoImpl extends ReadWriteDaoImpl<Reputation, Long> implements ReputationDao {

    private final EntityManager entityManager;

    @Autowired
    public ReputationDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Reputation> getReputationByUserId(Long userId) { // transfer to dtodao?
        TypedQuery<Reputation> query = entityManager.createQuery("FROM Reputation WHERE user.id =: userId", Reputation.class)
                                                    .setParameter("userId", userId);
        return SingleResultUtil.getSingleResultOrNull(query);
    }
}
