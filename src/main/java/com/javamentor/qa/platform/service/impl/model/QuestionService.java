package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.impl.model.QuestionDao;
import com.javamentor.qa.platform.models.entity.question.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService extends ReadWriteServiceImpl<Question, Long> {

    @Autowired
    public QuestionService(QuestionDao questionDao) {
        super(questionDao);
    }
}
