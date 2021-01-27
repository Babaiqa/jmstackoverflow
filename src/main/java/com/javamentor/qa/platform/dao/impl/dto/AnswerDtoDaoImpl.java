package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.AnswerDtoDao;
import com.javamentor.qa.platform.dao.impl.dto.transformers.QuestionResultTransformer;
import com.javamentor.qa.platform.models.dto.AnswerDto;
import com.javamentor.qa.platform.models.dto.CreateAnswerDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class AnswerDtoDaoImpl implements AnswerDtoDao {

    @PersistenceContext
    private EntityManager entityManager;
//
//    private Long id;
//    private Long userId;
//    private Long questionId;
//    private String body;
//    private LocalDateTime persistDate;
//    private Boolean isHelpful;
//    private LocalDateTime dateAccept;
//    private int countValuable;
//    private String image;
//    private String nickName;
//
    @Override
    public List<AnswerDto> getAllAnswersByQuestionId(Long questionId) {

        System.out.println("OLOLOLOLOLOLOLO");
//        "SELECT answer.id AS answer_id, " +
//                "answer.user.id AS answer_userId, " +
//                "answer.question.id AS answer_questionId, " +
//                "answer.htmlBody AS answer_body, " +
//                "answer.persistDateTime AS answer_persistDate," +
//                "answer.isHelpful AS answer_isHelpful," +
//                "answer.dateAcceptTime AS answer_dateAccept," +
//                "answer.user.id AS answer_countValuable, " +
//                "u.imageLink AS answer_image," +
//                "u.fullName AS answer_nickName " +
//                "from Answer answer " +
//                "INNER JOIN answer.user u " +
//                "WHERE answer.question.id = :questionId"

//        AnswerDto answerDtoList =  (AnswerDto) entityManager.unwrap(Session.class)
//                .createQuery("SELECT a.id AS answer_id, " +
//                        "u.id AS answer_userId, " +
//                        "q.id AS answer_questionId, " +
//                        "a.htmlBody AS answer_body, " +
//                        "a.persistDateTime AS answer_persistDate, " +
//                        "a.isHelpful AS answer_isHelpful, " +
//                        "a.dateAcceptTime AS answer_dateAccept, " +
//                        "(select count(av.answer.id) from AnswerVote av where av.answer.id = a.id) as answer_countValuable, " +
//                        "u.imageLink AS answer_image," +
//                        "u.fullName AS answer_nickName " +
//                        "from Answer a " +
//                        "INNER JOIN a.user u " +
//                        "JOIN a.question q " +
//                        "WHERE a.id = :questionId")
//                .setParameter("questionId", 1L)
//                .unwrap(org.hibernate.query.Query.class)
//                .getSingleResult();

//        AnswerDto answerDtoList = (AnswerDto) entityManager
//                .createQuery("SELECT answer.id AS answer_id, " +
//                        "u.id AS answer_userId, " +
//                        "q.id AS answer_questionId, " +
//                        "answer.htmlBody AS answer_body, " +
//                        "answer.persistDateTime AS answer_persistDate, " +
//                        "answer.isHelpful AS answer_isHelpful, " +
//                        "answer.dateAcceptTime AS answer_dateAccept, " +
//                        "100 as answer_countValuable, " +
//                        "u.imageLink AS answer_image," +
//                        "u.fullName AS answer_nickName " +
//                        "from Answer answer " +
//                        "INNER JOIN answer.user u " +
//                        "JOIN answer.question q " +
//                        "WHERE answer.id = :questionId", AnswerDto.class)
//                .setParameter("questionId", 1L)
//                .getSingleResult();

        List<AnswerDto> answerDtoList =  (List<AnswerDto>) entityManager.unwrap(Session.class)
                .createQuery("SELECT new com.javamentor.qa.platform.models.dto.AnswerDto(a.id, u.id, q.id, " +
                        "a.htmlBody, a.persistDateTime, a.isHelpful, a.dateAcceptTime, " +
                        "(select count(av.answer.id) from AnswerVote AS av where av.answer.id = a.id), " +
                        "u.imageLink, u.fullName) " +
                        "from Answer as a " +
                        "INNER JOIN a.user as u " +
                        "JOIN a.question as q " +
                        "WHERE q.id = :questionId")
                .setParameter("questionId", questionId)
                .unwrap(org.hibernate.query.Query.class)
                .getResultList();

//
//
//
        System.out.println("YYYY SIZE  "+answerDtoList.size());
        System.out.println("YYYY   "+answerDtoList.get(0).getId());
        System.out.println("YYYY   "+answerDtoList.get(0).getUserId());
        System.out.println("YYYY   "+answerDtoList.get(0).getQuestionId());
        System.out.println("YYYY   "+answerDtoList.get(0).getBody());
        System.out.println("YYYY   "+answerDtoList.get(0).getPersistDate());
        System.out.println("YYYY   "+answerDtoList.get(0).getCountValuable());
        System.out.println("YYYY   "+answerDtoList.get(0).getImage());
//
//        Long count =  (Long) entityManager.unwrap(Session.class)
//                .createQuery("select count(av.answer.id) from AnswerVote AS av where av.answer.id = 1")
//                .getSingleResult();
//        System.out.println("COUNT___"+count);

        return answerDtoList;
    }
}

//    @Override
//    public Optional<QuestionDto> getQuestionDtoById(Long id) {
//
//        return (Optional<QuestionDto>) entityManager.unwrap(Session.class)
//                .createQuery("select question.id as question_id, " +
//                        " question.title as question_title," +
//                        "u.fullName as question_authorName," +
//                        " u.id as question_authorId, " +
//                        "u.imageLink as question_authorImage," +
//                        "question.description as question_description," +
//                        " question.viewCount as question_viewCount," +
//                        "(select count(a.question.id) from Answer a where a.question.id=:id) as question_countAnswer," +
//                        "(select count(v.question.id) from VoteQuestion v where v.question.id=:id) as question_countValuable," +
//                        "question.persistDateTime as question_persistDateTime," +
//                        "question.lastUpdateDateTime as question_lastUpdateDateTime, " +
//                        " tag.id as tag_id,tag.name as tag_name " +
//                        "from Question question  " +
//                        "INNER JOIN  question.user u" +
//                        "  join question.tags tag"
//                        + " where question.id=:id")
//                .setParameter("id", id)
//                .unwrap(org.hibernate.query.Query.class)
//                .setResultTransformer(new QuestionResultTransformer())
//                .uniqueResultOptional();
//    }
