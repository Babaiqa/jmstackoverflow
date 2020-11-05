package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.CommentQuestionDao;
import com.javamentor.qa.platform.dao.abstracts.model.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.Comment;
import com.javamentor.qa.platform.models.entity.CommentType;
import com.javamentor.qa.platform.models.entity.question.CommentQuestion;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.CommentQuestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CommentQuestionServiceImpl extends ReadWriteServiceImpl<CommentQuestion, Long> implements CommentQuestionService {


    CommentQuestionDao commentQuestionDao;

    public CommentQuestionServiceImpl(ReadWriteDao<CommentQuestion, Long> readWriteDao,
                                      CommentQuestionDao commentQuestionDao) {
        super(readWriteDao);
        this.commentQuestionDao = commentQuestionDao;
    }

    @Transactional
    @Override
    public CommentQuestion addCommentToQuestion(String commentText, Question question, User user) {
        CommentQuestion commentQuestion = new CommentQuestion();
        commentQuestion.setQuestion(question);
        commentQuestion.setComment(Comment.builder().text(commentText)
                .user(user)
                .commentType(CommentType.QUESTION)
                .build());

        commentQuestionDao.persist(commentQuestion);

        return commentQuestion;
    }
}
