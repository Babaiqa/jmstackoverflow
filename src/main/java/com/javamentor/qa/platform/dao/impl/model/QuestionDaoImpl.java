package com.javamentor.qa.platform.dao.impl.model;


import com.javamentor.qa.platform.dao.abstracts.model.QuestionDao;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.Tag;

import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class QuestionDaoImpl extends ReadWriteDaoImpl<Question, Long> implements QuestionDao {


    @Override
    public List<Tag> getAllTagOfQuestion(Question question) {
        return question.getTags();
    }
}
