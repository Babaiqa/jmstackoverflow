package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.VoteQuestionDao;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.VoteQuestionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteQuestionServiceImpl extends ReadWriteServiceImpl<VoteQuestion, Long> implements VoteQuestionService {

    public VoteQuestionServiceImpl(VoteQuestionDao voteQuestionDao) {
        super(voteQuestionDao);
    }


    @Override
    public boolean isUserAlreadyVoted(Question question, User user) {
        List<VoteQuestion> list = question.getVoteQuestions();
        for (VoteQuestion voteQuestion : list) {
            if (voteQuestion.getUser().getId().equals(user.getId())) {
                return true;
            }
        }
        return false;
    }
}
