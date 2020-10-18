package com.javamentor.qa.platform.service.impl.model;


import com.javamentor.qa.platform.dao.impl.model.QuestionDaoImpl;
import com.javamentor.qa.platform.models.entity.question.Question;
import org.springframework.stereotype.Service;

@Service
public class QuestionService extends ReadWriteServiceImpl<Question, Long>{
    public QuestionService(QuestionDaoImpl questionDaoImpl) {
        super(questionDaoImpl);
    }
}
