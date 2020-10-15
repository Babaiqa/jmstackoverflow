package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.impl.model.AnswerDao;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import org.springframework.stereotype.Service;

@Service
public class AnswerService extends ReadWriteServiceImpl<Answer, Long> {
    AnswerService(AnswerDao answerDao) {
        super(answerDao);
    }
}
