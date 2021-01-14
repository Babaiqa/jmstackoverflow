package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.AnswerDto;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public abstract class AnswerConverter {


    @Mapping(source = "answer.id", target = "id")
    @Mapping(source = "answer.question.id", target = "questionId")
    @Mapping(source = "answer.user.id", target = "userId")
    @Mapping(source = "answer.htmlBody", target = "body")
    public abstract AnswerDto answerToAnswerDTO(Answer answer);

}