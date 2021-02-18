package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.QuestionDtoDao;
import com.javamentor.qa.platform.dao.impl.dto.transformers.QuestionResultTransformer;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import com.javamentor.qa.platform.webapp.converters.QuestionConverter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class QuestionDtoDaoImpl implements QuestionDtoDao {

    @PersistenceContext
    private EntityManager entityManager;
    private QuestionConverter converter;

    @Autowired
    public QuestionDtoDaoImpl(QuestionConverter converter) {
        this.converter = converter;
    }

    @Override
    public Optional<QuestionDto> getQuestionDtoById(Long id) {

        Question question = (Question) entityManager.createQuery("FROM Question Q WHERE Q.id = ?1")
                .setParameter(1, id).getSingleResult();
        return  Optional.of(converter.questionToQuestionDto(question));
    }
}