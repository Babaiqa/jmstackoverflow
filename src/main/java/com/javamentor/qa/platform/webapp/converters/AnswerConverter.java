package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.AnswerDto;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;


@Mapper(componentModel = "spring")
public abstract class AnswerConverter {

    @Mapping(source = "answer.id", target = "id")
    @Mapping(source = "answer.user.id", target = "userId")
    @Mapping(source = "answer.question.id", target = "questionId")
    @Mapping(source = "answer.htmlBody", target = "body")
    @Mapping(source = "answer.persistDateTime", target = "persistDate")
    @Mapping(source = "answer.isHelpful", target = "isHelpful")
    @Mapping(source = "answer.dateAcceptTime", target = "dateAccept")
    @Mapping(source = "answer.voteAnswers", target = "countValuable", qualifiedByName = "voteAnswersToCountValuable")
    @Mapping(source = "answer.user.imageLink", target = "image")
    @Mapping(source = "answer.user.nickname", target = "nickName")
    public abstract AnswerDto answerToAnswerDTO(Answer answer);

    @Named(value = "voteAnswersToCountValuable")
    public Long voteAnswersToCountValuable(List<VoteAnswer> list) {
        long countValuable = 0L;
        for (VoteAnswer voteAnswer : list) {
            countValuable = countValuable + voteAnswer.getVote();
        }
        return countValuable;
    }
}