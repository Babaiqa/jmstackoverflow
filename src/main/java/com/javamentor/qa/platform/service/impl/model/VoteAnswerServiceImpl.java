package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.AnswerDao;
import com.javamentor.qa.platform.dao.abstracts.model.VoteAnswerDao;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.VoteAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteAnswerServiceImpl extends ReadWriteServiceImpl<VoteAnswer, Long> implements VoteAnswerService {

    public VoteAnswerServiceImpl(VoteAnswerDao voteAnswerDao) {
        super(voteAnswerDao);
    }

    @Override
    public boolean isUserAlreadyVotedUp(Answer answer, User user) {
        List<VoteAnswer> voteAnswers = answer.getVoteAnswers();
        for (VoteAnswer voteAnswer : voteAnswers) {
            if (voteAnswer.getUser().getId().equals(user.getId())) {
                voteAnswer.setVote(1);
                super.update(voteAnswer);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isUserAlreadyVotedDown(Answer answer, User user) {
        List<VoteAnswer> voteAnswers = answer.getVoteAnswers();
        for (VoteAnswer voteAnswer : voteAnswers) {
            if (voteAnswer.getUser().getId().equals(user.getId())) {
                voteAnswer.setVote(-1);
                super.update(voteAnswer);
                return true;
            }
        }
        return false;
    }

}
