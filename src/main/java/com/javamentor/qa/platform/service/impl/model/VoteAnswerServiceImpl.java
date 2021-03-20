package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.VoteAnswerDao;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.VoteAnswerService;
import org.springframework.stereotype.Service;



@Service
public class VoteAnswerServiceImpl extends ReadWriteServiceImpl<VoteAnswer, Long> implements VoteAnswerService {

    public VoteAnswerServiceImpl(VoteAnswerDao voteAnswerDao) {
        super(voteAnswerDao);
    }

    @Override
    public boolean isUserAlreadyVotedIsThisQuestion(Question question, User user, Answer answer) {
        return answer.getVoteAnswers().stream()
                     .anyMatch(listItem -> listItem.getUser().getId().equals(user.getId())) ||
                question.getAnswers().stream()
                        .anyMatch(answerListItem -> answerListItem.getVoteAnswers()
                                .stream().anyMatch(voteAnswer -> voteAnswer.getUser().getId().equals(user.getId())));
    }
}