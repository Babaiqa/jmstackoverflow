package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.dao.impl.model.QuestionDaoImpl;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public interface QuestionConverter {
    @Autowired
    QuestionDaoImpl questionDaoImpl = null;

    @Mapping(source="question.id",target="id")
        @Mapping(source="question.user.id",target="authorId")
        @Mapping(source="question.title",target="title")
        @Mapping(source="question.description",target="description")
        @Mapping(source="question.user.fullName",target="authorName")
        @Mapping(source="question.user.imageLink",target="authorImage")
        @Mapping(source="question.viewCount",target="viewCount")
        @Mapping(source="question.tags",target="listTagDto")
        @Mapping(source="question.persistDateTime",target="persistDateTime")
        @Mapping(source="question.lastUpdateDateTime",target="lastUpdateDateTime")


    QuestionDto questionToQuestionDto(Question question);



    @Mapping(source="questionDto.id",target="id")
    @Mapping(source="questionDto.authorId",target="user.setId()")
    @Mapping(source="questionDto.title",target="title")
    @Mapping(source="questionDto.description",target="description")
    @Mapping(source="questionDto.user.getUserName()",target="authorName")
    @Mapping(source="questionDto.user.getImageLink()",target="authorImage")
    @Mapping(source="questionDto.viewCount",target="viewCount")
    @Mapping(source="questionDto.tags",target="listTagDto")
    @Mapping(source="questionDto.persistDateTime",target="persistDateTime")
    @Mapping(source="questionDto.lastUpdateDateTime",target="lastUpdateDateTime")
    @Mapping(source="questionDto.isDeleted",target="isHelpful")


    Question questionDtoToQuestion(QuestionDto questionDto);

}
