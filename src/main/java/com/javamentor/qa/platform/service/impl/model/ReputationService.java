package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.impl.model.ReputationDaoImpl;
import com.javamentor.qa.platform.models.entity.user.Reputation;
import org.springframework.stereotype.Service;

@Service
public class ReputationService extends ReadWriteServiceImpl<Reputation, Long> {
    public ReputationService(ReputationDaoImpl reputationDaoImpl) {
        super(reputationDaoImpl);
    }
}
