package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import org.mapstruct.*;

@Mapper
public abstract class QuestionConverter {

    @Mapping(source = "question.id", target = "id")
    @Mapping(source = "question.user.id", target = "authorId")
    @Mapping(source = "question.title", target = "title")
    @Mapping(source = "question.description", target = "description")
    @Mapping(source = "question.user.fullName", target = "authorName")
    @Mapping(source = "question.user.imageLink", target = "authorImage")
    @Mapping(source = "question.viewCount", target = "viewCount")
    @Mapping(source = "question.tags", target = "listTagDto")
    @Mapping(source = "question.persistDateTime", target = "persistDateTime")
    @Mapping(source = "question.lastUpdateDateTime", target = "lastUpdateDateTime")

    public abstract QuestionDto questionToQuestionDto(Question question);


    @Mapping(source = "questionDto.id", target = "id")
    @Mapping(source = "questionDto.authorId", target = "user.id")
    @Mapping(source = "questionDto.title", target = "title")
    @Mapping(source = "questionDto.description", target = "description")
    @Mapping(source = "questionDto.authorName", target = "user.fullName")
    @Mapping(source = "questionDto.authorImage", target = "user.imageLink")
    @Mapping(source = "questionDto.viewCount", target = "viewCount")
    @Mapping(source = "questionDto.listTagDto", target = "tags")
    @Mapping(source = "questionDto.persistDateTime", target = "persistDateTime")
    @Mapping(source = "questionDto.lastUpdateDateTime", target = "lastUpdateDateTime")

    public abstract Question questionDtoToQuestion(QuestionDto questionDto);

}
