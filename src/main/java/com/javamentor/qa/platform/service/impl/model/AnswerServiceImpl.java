package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.AnswerDao;
import com.javamentor.qa.platform.dao.abstracts.model.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnswerServiceImpl extends ReadWriteServiceImpl<Answer, Long> implements AnswerService {

    private final AnswerDao answerDao;

    public AnswerServiceImpl(ReadWriteDao<Answer, Long> readWriteDao, AnswerDao answerDao) {
        super(readWriteDao);
        this.answerDao = answerDao;
    }


    @Transactional
    @Override
    public Answer addAnswerToQuestion(String htmlBody, Question question, User user) {
        Answer answer = new Answer(question, user, htmlBody, false, false);
        answer.setQuestion(question);

        answerDao.persist(answer);

        return answer;
    }
}
