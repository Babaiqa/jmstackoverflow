package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class QuestionConverter {


    @Autowired
    private UserService userService;

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
    @Mapping(source = "question.voteQuestions", target = "countValuable", qualifiedByName = "voteQuestionsToCountValuable")
    public abstract QuestionDto questionToQuestionDto(Question question);


    @Mapping(target = "user", source = "userId",qualifiedByName = "mapUser")
    public abstract Question questionCreateDtoToQuestion(QuestionCreateDto questionCreateDto);

    @Named("mapUser")
    public User mapUser(Long id) {
        return userService.getById(id).get();
    }


    @Named(value = "voteQuestionsToCountValuable")
    public Long voteQuestionsToCountValuable(List<VoteQuestion> list) {
        long countValuable = 0L;
        for (VoteQuestion voteQuestion : list) {
            countValuable = countValuable + voteQuestion.getVote();
        }
        return countValuable;
    }
}
