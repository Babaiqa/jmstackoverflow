package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;

public interface QuestionConverter {
    QuestionDto questionToDto(Question question);
    Question dtoToQuestion(QuestionDto questionDto);
}
