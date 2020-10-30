package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.QuestionDto;

import java.util.List;
import java.util.Optional;

public interface QuestionDtoDao {
     Optional<QuestionDto> getQuestionDtoById(Long id);

     int getTotalResultCountQuestionDto();

     List<QuestionDto> getPagination(int page, int size);
}
