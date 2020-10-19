package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import org.mapstruct.*;

@Mapper
public interface QuestionConverter {

        @Mapping(source="serialVersionUID",target="serialVersionUID")
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


            @Mapping(source="questionDto.id",target="id")
            @Mapping(source="questionDto.title",target="title")


    Question questionDtoToQuestion(QuestionDto questionDto);

}
