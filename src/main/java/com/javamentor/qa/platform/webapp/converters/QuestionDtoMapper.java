package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import org.mapstruct.Mapper;

public interface QuestionDtoMapper {
    @Mapper
    public interface SimpleSourceDestinationMapper {
        QuestionDto questionToDto(Question question);
        Question dtoToQuestion(QuestionDto questionDto);
    }
}
