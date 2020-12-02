package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.Tag;

import java.util.List;


public interface QuestionDao extends ReadWriteDao<Question, Long> {

    long getTotalCountQuestionsWithoutTags(List<Long> tagIds);

    List<Tag> getAllTagOfQuestion(Question question);

    List<Question> getPaginationQuestionsWithoutTags(int page, int size, List<Long> tagIds);
}
