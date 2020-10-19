package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import org.mapstruct.*;

@Mapper
public interface QuestionConverter {


        @Mapping(source="question.serialVersionUID",target="serialVersionUID")
        @Mapping(source="question.user.getId()",target="authorId")
        @Mapping(source="question.title",target="title")
        @Mapping(source="question.description",target="description")
        @Mapping(source="question.user.getUserName()",target="authorName")
        @Mapping(source="question.user.getImageLink()",target="authorImage")
        @Mapping(source="question.viewCount",target="viewCount")
        @Mapping(source="question.tags",target="listTagDto")
        @Mapping(source="question.persistDateTime",target="persistDateTime")
        @Mapping(source="question.lastUpdateDateTime",target="lastUpdateDateTime")
        @Mapping(source="question.isDeleted",target="isHelpful")


    QuestionDto questionToQuestionDto(Question question);


//    @Mapping(source="questionDto.serialVersionUID",target="serialVersionUID")
//    @Mapping(source="questionDto.authorId",target="user.setId()")
//    @Mapping(source="questionDto.title",target="title")
//    @Mapping(source="questionDto.description",target="description")
//    @Mapping(source="questionDto.user.getUserName()",target="authorName")
//    @Mapping(source="questionDto.user.getImageLink()",target="authorImage")
//    @Mapping(source="questionDto.viewCount",target="viewCount")
//    @Mapping(source="questionDto.tags",target="listTagDto")
//    @Mapping(source="questionDto.persistDateTime",target="persistDateTime")
//    @Mapping(source="questionDto.lastUpdateDateTime",target="lastUpdateDateTime")
//    @Mapping(source="questionDto.isDeleted",target="isHelpful")


    Question questionDtoToQuestion(QuestionDto questionDto);

}
