package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.CommentDto;
import com.javamentor.qa.platform.models.entity.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class CommentConverter {

    public CommentDto commentToCommentDTO(Comment comment) {
        return new CommentDto(comment.getId(), comment.getText(), comment.getPersistDateTime(),
                comment.getLastUpdateDateTime(), comment.getCommentType(), comment.getUser().getId(),
                comment.getUser().getFullName(), comment.getUser().getReputationCount().longValue());
    }
}
