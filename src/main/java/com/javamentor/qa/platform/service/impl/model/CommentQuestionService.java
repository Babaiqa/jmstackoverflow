package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.impl.model.CommentQuestionDaoImpl;
import com.javamentor.qa.platform.models.entity.question.CommentQuestion;
import org.springframework.stereotype.Service;

@Service
public class CommentQuestionService extends ReadWriteServiceImpl<CommentQuestion, Long> {
    public CommentQuestionService(CommentQuestionDaoImpl commentQuestionDao) {
        super(commentQuestionDao);
    }
}
