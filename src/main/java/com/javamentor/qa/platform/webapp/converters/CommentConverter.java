package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.CommentDto;
import com.javamentor.qa.platform.models.entity.question.CommentQuestion;
import com.javamentor.qa.platform.models.entity.question.answer.CommentAnswer;
import com.javamentor.qa.platform.models.entity.user.Reputation;
import com.javamentor.qa.platform.service.abstracts.model.ReputationService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.text.html.Option;
import java.util.Optional;


@Mapper(componentModel = "spring")
public abstract class CommentConverter {

    private final ReputationService reputationService;

    @Autowired
    protected CommentConverter(ReputationService reputationService) {
        this.reputationService = reputationService;
    }

    @Mapping(source = "comment", target = ".")
    @Mapping(source = "comment.persistDateTime", target = "persistDate")
    @Mapping(source = "comment.lastUpdateDateTime", target = "lastRedactionDate")
    @Mapping(source = "comment.user.id", target = "userId")
    @Mapping(source = "comment.user.fullName", target = "username")
    @Mapping(source = "comment.user.id", target = "reputation", qualifiedByName = "getReputationCount")
    public abstract CommentDto commentToCommentDTO(CommentQuestion commentQuestion);

    @Mapping(source = "comment", target = ".")
    @Mapping(source = "comment.persistDateTime", target = "persistDate")
    @Mapping(source = "comment.lastUpdateDateTime", target = "lastRedactionDate")
    @Mapping(source = "comment.user.id", target = "userId")
    @Mapping(source = "comment.user.fullName", target = "username")
    @Mapping(source = "comment.user.id", target = "reputation", qualifiedByName = "getReputationCount")
    public abstract CommentDto commentToCommentDTO(CommentAnswer commentAnswer);

    @Named("getReputationCount")
    public Integer getReputationCountByUserId(Long userId) {
        Optional<Reputation> rep = reputationService.getReputationByUserId(userId);
        if (rep.isPresent()) {
            return rep.get().getCount();
        }
        throw new IllegalArgumentException(String.format("Tuple in reputation table, that must be connected, with user ID %d not found", userId));
    }
}
