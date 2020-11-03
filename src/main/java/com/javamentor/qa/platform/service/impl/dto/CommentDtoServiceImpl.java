package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.CommentDtoDao;
import com.javamentor.qa.platform.models.dto.CommentDto;
import com.javamentor.qa.platform.service.abstracts.model.CommentDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentDtoServiceImpl implements CommentDtoService {

    private  final CommentDtoDao commentDtoDao;

    @Autowired
    public CommentDtoServiceImpl(CommentDtoDao commentDtoDao) {
        this.commentDtoDao = commentDtoDao;
    }

    @Override
    public Optional<CommentDto> getCommentDtoById(Long commentId) {
        commentDtoDao.getCommentDtoById(commentId);

        return Optional.empty();
    }
}
