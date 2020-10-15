package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.impl.model.CommentAnswerDao;
import com.javamentor.qa.platform.models.entity.question.answer.CommentAnswer;
import org.springframework.stereotype.Service;

@Service
public class CommentAnswerService extends ReadWriteServiceImpl<CommentAnswer, Long> {
    public CommentAnswerService(CommentAnswerDao commentAnswerDao) {
        super(commentAnswerDao);
    }
}
