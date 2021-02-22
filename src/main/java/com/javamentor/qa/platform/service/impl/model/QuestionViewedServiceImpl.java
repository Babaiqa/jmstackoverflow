package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.QuestionViewedDao;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.QuestionViewed;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.QuestionViewedService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionViewedServiceImpl extends ReadWriteServiceImpl<QuestionViewed, Long> implements QuestionViewedService {

    private final QuestionViewedDao questionViewedDao;

    public QuestionViewedServiceImpl(QuestionViewedDao questionViewedDao) {
        super(questionViewedDao);
        this.questionViewedDao = questionViewedDao;
    }


    @Transactional
    @Override
    public void markQuestionAsViewed(Optional<Question> question, User user) {

        Question CurrentQuestion = question.get();
        List<QuestionViewed> listOfUserViewedQuestion = CurrentQuestion.getUserViewedQuestions();
        boolean isQuestionAlreadyMarked = false;
        for (QuestionViewed questionViewed : listOfUserViewedQuestion) {
            if (questionViewed.getUser().equals(user)) {
                isQuestionAlreadyMarked = true;
            }
        }
        if (!isQuestionAlreadyMarked) {
            QuestionViewed questionViewed = new QuestionViewed();
            questionViewed.setQuestion(question.get());
            questionViewed.setUser(user);
            questionViewedDao.persist(questionViewed);

        }
    }

}
