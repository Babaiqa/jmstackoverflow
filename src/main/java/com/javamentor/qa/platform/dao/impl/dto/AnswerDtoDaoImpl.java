package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.AnswerDtoDao;
import com.javamentor.qa.platform.models.dto.AnswerDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import com.javamentor.qa.platform.webapp.converters.AnswerConverter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AnswerDtoDaoImpl implements AnswerDtoDao {

    @PersistenceContext
    private EntityManager entityManager;
    private AnswerConverter answerConverter;

    @Autowired
    public AnswerDtoDaoImpl(AnswerConverter answerConverter) {
        this.answerConverter = answerConverter;
    }

    @Override
    public List<AnswerDto> getAllAnswersByQuestionId(Long questionId) {

        Question question = (Question) entityManager.createQuery("FROM Question Q WHERE Q.id = ?1")
                .setParameter(1,questionId).getSingleResult();
        List<Answer> answers = question.getAnswers();
        List<AnswerDto> answerDtos = new ArrayList<>();
        for (Answer answer : answers) {
            answerDtos.add(answerConverter.answerToAnswerDTO(answer));
        }
        return answerDtos;
    }
}
