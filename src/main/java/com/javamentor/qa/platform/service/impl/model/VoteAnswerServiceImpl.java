package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.ReadWriteDao;
import com.javamentor.qa.platform.dao.abstracts.model.VoteAnswerDao;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.security.util.SecurityHelper;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.abstracts.model.VoteAnswerService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VoteAnswerServiceImpl extends ReadWriteServiceImpl<VoteAnswer, Long> implements VoteAnswerService {

    private final SecurityHelper securityHelper;
    private final AnswerService answerService;
    private final VoteAnswerDao voteAnswerDao;

//    public VoteAnswerServiceImpl(ReadWriteDao<VoteAnswer, Long> readWriteDao, VoteAnswerDao voteAnswerDao) {
//        super(readWriteDao);
//        this.voteAnswerDao = voteAnswerDao;
//    }

    public VoteAnswerServiceImpl(ReadWriteDao<VoteAnswer, Long> readWriteDao,
                                 VoteAnswerDao voteAnswerDao,
                                 SecurityHelper securityHelper,
                                 AnswerService answerService) {
        super(readWriteDao);
        this.voteAnswerDao = voteAnswerDao;
        this.securityHelper = securityHelper;
        this.answerService = answerService;
    }

    // FIXME:: Нельзя так делать, этот answer пришел к тебе
    //  без VoteAnswer т.к. ты получеешь этот анвер по id,
    //  но когда ты просишь у него лист VoteAnswer, то хибернейт
    //  начинает делать кучу запросов в бд равному количетсву
    //  item в листе, перепиши на запрос
    //  Эту логику можно просто сделать через обычный 1 sql запрос
    @Override
    public boolean isUserAlreadyVoted(Answer answer, User user) {
        List<VoteAnswer> list = answer.getVoteAnswers();
        for (VoteAnswer voteAnswer : list) {
            if (voteAnswer.getUser().getId().equals(user.getId())) {
                return true;
            }
        }
        return false;
    }

    // FIXME:: Опять же нельзя доставать у questiom юзера т.к.
    //  ты получил этот вопрос по id из контроллера и т.к.
    //  у нас стоит lazy он приходит без него дальше проблема
    //  которую я описывал выше
    // FIXME:: этот метод из контроллера убрать и вынести в сервис
    public void markHelpful(Optional<Question> question, Optional<Answer> answer, boolean isHelpful) {
        boolean authorOfQuestion = question.get().getUser().getId().equals(securityHelper.getPrincipal().getId());
        if (authorOfQuestion) {
            answer.get().setIsHelpful(isHelpful);
            answer.get().setDateAcceptTime(LocalDateTime.now());
            answerService.update(answer.get());
        }
    }

    @Override
    public boolean isUserAlreadyVotedIsThisQuestion(Question question, User user, Answer answer) {

        return voteAnswerDao.isUserAlreadyVotedIsThisQuestion(question, user, answer);

    }
}