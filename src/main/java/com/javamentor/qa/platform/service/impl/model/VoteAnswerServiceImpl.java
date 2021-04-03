package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.ReadWriteDao;
import com.javamentor.qa.platform.dao.abstracts.model.VoteAnswerDao;
import com.javamentor.qa.platform.models.dto.VoteAnswerDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.VoteAnswerDtoService;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.abstracts.model.VoteAnswerService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VoteAnswerServiceImpl extends ReadWriteServiceImpl<VoteAnswer, Long> implements VoteAnswerService {

    private final AnswerService answerService;
    private final VoteAnswerDao voteAnswerDao;
    private final VoteAnswerDtoService voteAnswerDtoService;

    public VoteAnswerServiceImpl(ReadWriteDao<VoteAnswer, Long> readWriteDao,
                                 VoteAnswerDao voteAnswerDao,
                                 AnswerService answerService,
                                 VoteAnswerDtoService voteAnswerDtoService) {
        super(readWriteDao);
        this.voteAnswerDao = voteAnswerDao;
        this.answerService = answerService;
        this.voteAnswerDtoService = voteAnswerDtoService;
    }

    // FIXME:: Опять же нельзя доставать у questiom юзера т.к.
    //  ты получил этот вопрос по id из контроллера и т.к.
    //  у нас стоит lazy он приходит без него дальше проблема
    //  которую я описывал выше
    // FIXME:: этот метод из контроллера убрать и вынести в сервис
    public void markHelpful(Question question, User user, Answer answer, boolean isHelpful) {

        boolean authorOfQuestion = question.getUser().getId().equals(user.getId());
        if (authorOfQuestion) {
            answer.setIsHelpful(isHelpful);
            answer.setDateAcceptTime(LocalDateTime.now());
            answerService.update(answer);
        }
    }

    @Override
    public boolean isUserAlreadyVotedIsThisQuestion(Question question, User user, Answer answer) {

        return voteAnswerDao.isUserAlreadyVotedIsThisQuestion(question, user, answer);
    }

    @Override
    public ResponseEntity<String> upVoteIfAlreadyVoted(Question question, User user, Answer answer) {

        if (isUserAlreadyVotedIsThisQuestion(question, user, answer)) {
            Optional<VoteAnswerDto> optionalVoteAnswerDto = voteAnswerDtoService.getVoteByAnswerIdAndUserId(answer.getId(), user.getId());
            if (optionalVoteAnswerDto.isPresent()) {
                int voteValue = optionalVoteAnswerDto.get().getVote();
                if (voteValue == 1) {
                    voteAnswerDao.deleteById(optionalVoteAnswerDto.get().getId());
                    markHelpful(question, user, answer, false);
                } else if (voteValue == -1) {
                    voteAnswerDao.deleteById(optionalVoteAnswerDto.get().getId());
                    VoteAnswer voteAnswer = new VoteAnswer(user, answer, 1);
                    voteAnswerDao.persist(voteAnswer);
                    markHelpful(question, user, answer, true);
                }
                return ResponseEntity.ok("Vote changed");
            }
            return ResponseEntity.ok("Can't change vote");
        }
        return ResponseEntity.ok("OK");
    }
}