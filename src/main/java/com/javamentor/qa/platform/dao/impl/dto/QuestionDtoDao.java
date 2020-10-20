package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import org.hibernate.Session;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Repository
public class QuestionDtoDao {

    @PersistenceContext
    EntityManager entityManager;

    private  final String QUERY_QUESTIONDTO ="select q.id as q_id, " +
            " q.title as q_title," +
            "u.fullName as q_authorName," +
            " u.id as q_authorId, " +
            "u.imageLink as q_authorImage," +
            "q.description as q_description," +
            " q.viewCount as q_viewCount," +
            "(select count(a.question.id) from Answer a where a.question.id=:id) as q_countAnswer," +
            "(select CASE WHEN count(an.isHelpful) > 0 THEN true ELSE false END from Answer an" +
            " where an.question.id=:id and an.isHelpful=true) as q_isHelpful," +
            "(select count(v.question.id) from VoteQuestion v where v.question.id=:id) as q_countValuable," +
            "q.persistDateTime as q_persistDateTime," +
            "q.lastUpdateDateTime as q_lastUpdateDateTime, " +

            " t.id as id,t.name as name " +
            "from Question q  " +
            "INNER JOIN  q.user u" +
            "  join q.tags t";


    public  Optional<QuestionDto> getQuestionDtoById(Long id) {

        Optional<QuestionDto> questionDto = entityManager.unwrap(Session.class)
                .createQuery(QUERY_QUESTIONDTO+" where q.id=:id")
                .setParameter("id", id)
                .unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(new ResultTransformer() {
                    private Map<Long, QuestionDto> questionDtoMap = new LinkedHashMap<>();

                    @Override
                    public Object transformTuple(Object[] tuple, String[] aliases) {

                        Map<String,Integer> aliasToIndexMap= aliasToIndexMap(aliases);
                        Long questionId=((Number)tuple[aliasToIndexMap.get(QuestionDto.ID_ALIAS)]).longValue();

                        QuestionDto questionDto = questionDtoMap.computeIfAbsent(
                                questionId,
                                id -> new QuestionDto(tuple, aliasToIndexMap)
                        );

                        questionDto.getListTagDto().add(
                                new TagDto(tuple,aliasToIndexMap)
                        );

                        return questionDto;
                    }


                    @Override
                    public List transformList(List list) {
                        return new ArrayList<>(questionDtoMap.values());
                    }


                    public  Map<String, Integer> aliasToIndexMap(
                            String[] aliases) {

                        Map<String, Integer> aliasToIndexMap = new LinkedHashMap<>();

                        for (int i = 0; i < aliases.length; i++) {
                            aliasToIndexMap.put(aliases[i], i);
                        }

                        return aliasToIndexMap;
                    }
                })
                .uniqueResultOptional();
        return  questionDto;
    }





}