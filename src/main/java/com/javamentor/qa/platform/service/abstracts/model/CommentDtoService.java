package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.dto.CommentDto;

import java.util.Optional;

public interface CommentDtoService {
    Optional<CommentDto> getCommentDtoById(Long commentId);
}
