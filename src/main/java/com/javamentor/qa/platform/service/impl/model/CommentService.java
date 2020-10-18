package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.impl.model.CommentDaoImpl;
import com.javamentor.qa.platform.models.entity.Comment;
import org.springframework.stereotype.Service;

@Service
public class CommentService extends ReadWriteServiceImpl<Comment, Long> {
    public CommentService (CommentDaoImpl commentDaoImpl) {
        super(commentDaoImpl);
    }
}
