package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.impl.model.AnswerDaoImpl;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import org.springframework.stereotype.Service;

@Service
public class AnswerService extends ReadWriteServiceImpl<Answer, Long> {
    AnswerService(AnswerDaoImpl answerDaoImpl) {
        super(answerDaoImpl);
    }
}
