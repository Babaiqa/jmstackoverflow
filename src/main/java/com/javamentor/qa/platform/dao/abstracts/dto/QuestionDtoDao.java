package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface QuestionDtoDao {
     Optional<QuestionDto> getQuestionDtoById(Long id);

     int getTotalResultCountQuestionDto();

     List<Question> getPagination(int page, int size);

     List<QuestionDto> getQuestionDtoByTagIds(List<Long> ids);

     List<QuestionDto> getPaginationOrderedNew(int page, int size);

     List<QuestionDto> getQuestionTagsByQuestionIds(List<Long> ids);

     List<Long> getQuestionsNotAnsweredIDs(int page, int size);

     List<QuestionDto> getQuestionDtoByIds(List<Long> ids);

     long getTotalCountQuestionNotAnswer();

    List<QuestionDto> getQuestionWithGivenTags(int page, int size, List<Long> tagIds);

     List<QuestionDto> getQuestionBySearchValue(Map<String, String> data);
}
