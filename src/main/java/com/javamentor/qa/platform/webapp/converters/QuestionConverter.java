package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import org.mapstruct.*;

@Mapper
public interface QuestionConverter {

        @Mapping(source="question.id",target="id")
        @Mapping(source="question.title",target="title")


QuestionDto questionToQuestionDto(Question question);


            @Mapping(source="questionDto.id",target="id")
            @Mapping(source="questionDto.title",target="title")


    Question questionDtoToQuestion(QuestionDto questionDto);

}
