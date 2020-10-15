package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.impl.model.CommentQuestionDao;
import com.javamentor.qa.platform.models.entity.question.CommentQuestion;
import org.springframework.stereotype.Service;

@Service
public class CommentQuestionService extends ReadWriteServiceImpl<CommentQuestion, Long> {
    public CommentQuestionService(CommentQuestionDao commentQuestionDao) {
        super(commentQuestionDao);
    }
}
