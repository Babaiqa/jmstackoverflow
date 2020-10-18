package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.impl.model.UserFavoriteQuestionDaoImpl;
import com.javamentor.qa.platform.models.entity.user.UserFavoriteQuestion;
import org.springframework.stereotype.Service;

@Service
public class UserFavoriteQuestionService extends ReadWriteServiceImpl<UserFavoriteQuestion, Long>{
    public UserFavoriteQuestionService(UserFavoriteQuestionDaoImpl userFavoriteQuestionDaoImpl) {
        super(userFavoriteQuestionDaoImpl);
    }
}
