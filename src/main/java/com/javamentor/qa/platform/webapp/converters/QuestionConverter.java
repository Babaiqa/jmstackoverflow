package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import org.springframework.beans.BeanUtils;

public class QuestionConverter implements QuestionDtoMapper{

    public Question questionToDto(QuestionDto questionDto) {
        return null;
    }

    public QuestionDto dtoToQuestion(Question question) {
        return null;
    }
}
