package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.ReputationDao;
import com.javamentor.qa.platform.models.entity.user.Reputation;
import com.javamentor.qa.platform.service.abstracts.model.ReputationService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class ReputationServiceImpl extends ReadWriteServiceImpl<Reputation, Long> implements ReputationService {

    private final ReputationDao reputationDao;

    private final Random r;


    public ReputationServiceImpl(ReputationDao reputationDao) {
        super(reputationDao);
        r = new Random();
        this.reputationDao = reputationDao;
    }

    @Override
    public Optional<Reputation> getReputationByUserId(Long userId) {
        return reputationDao.getReputationByUserId(userId);
    }

    @Override
    public void persist(Reputation reputation) {
        reputation.setCount(r.nextInt(100));
        super.persist(reputation);
    }
}
