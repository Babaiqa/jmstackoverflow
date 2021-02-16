package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.VoteAnswerDao;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.VoteAnswerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteAnswerServiceImpl extends ReadWriteServiceImpl<VoteAnswer, Long> implements VoteAnswerService {


    public VoteAnswerServiceImpl(VoteAnswerDao voteAnswerDao) {
        super(voteAnswerDao);
    }

    @Override
    public boolean isUserAlreadyVoted(Answer answer, User user) {
        List<VoteAnswer> voteAnswerList = answer.getVoteAnswers();
        for (VoteAnswer voteAnswer : voteAnswerList) {
            if (voteAnswer.getUser().getId().equals(user.getId())) {
                return true;
            }
        }
        return false;
    }

}
