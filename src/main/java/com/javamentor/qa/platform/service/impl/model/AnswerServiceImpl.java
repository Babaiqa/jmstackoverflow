package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.security.util.SecurityHelper;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class AnswerServiceImpl extends ReadWriteServiceImpl<Answer, Long> implements AnswerService {

    public AnswerServiceImpl(ReadWriteDao<Answer, Long> readWriteDao) {
        super(readWriteDao);
    }


    @Override
    public void markAnswerIsHelpfulTrue(Answer answer) {
        answer.setIsHelpful(true);
        answer.setDateAcceptTime(LocalDateTime.now());
    }
}

