package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.dto.TagListDto;
import org.hibernate.Session;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Repository
public class TagDtoDaoImpl implements TagDtoDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<TagDto> getTagDtoPagination(int page, int size) {

        return entityManager.createQuery(
                "select new com.javamentor.qa.platform.models.dto.TagDto(tag.id,tag.name)" +
                        " from Tag  tag order by tag.questions.size desc, tag.id ")
                .setFirstResult(page*size-size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public List<TagListDto> getTagDtoPaginationOrderByAlphabet(int page, int size) {


        String qwery = "Select tag.id as id, tag.name as  name , tag.description as description," +
                " (SELECT COUNT(question_has_tag.question_id) FROM question_has_tag join question on question_has_tag.question_id=question.id WHERE question_has_tag.tag_id = tag.id) as countQuestion," +
                " (SELECT COUNT(question_has_tag.question_id) FROM question_has_tag join question on question_has_tag.question_id=question.id WHERE question_has_tag.tag_id = tag.id and question.persist_date >= now() - interval '1 week')as countQuestionToWeek," +
                " (SELECT COUNT(question_has_tag.question_id) FROM question_has_tag join question on question_has_tag.question_id=question.id WHERE question_has_tag.tag_id = tag.id and question.persist_date >= now() - interval '1 day')as countQuestionToDay" +
                " from question_has_tag join question on question.id = question_has_tag.question_id join tag on tag.id =question_has_tag.tag_id" +
                " GROUP BY tag.id";





        return entityManager.unwrap(Session.class)
                .createNativeQuery(qwery)
                .unwrap(org.hibernate.query.NativeQuery.class)
                .setResultTransformer(new ResultTransformer() {

                    private Map<Long, TagListDto> tagListDtoMap = new LinkedHashMap<>();


                    @Override
                    public Object transformTuple(Object[] tuple, String[] aliases) {

                        Map<String,Integer> aliasToIndexMap = aliasToIndexMap(aliases);
                        Long questionId=((Number) tuple[0]).longValue();

                        TagListDto tagListDto1 = tagListDtoMap.computeIfAbsent(
                                questionId,
                                id1 -> {
                                    TagListDto tagListDtoTemp = new TagListDto();
                                    tagListDtoTemp.setId(((Number) tuple[aliasToIndexMap.get("id")]).longValue());
                                    tagListDtoTemp.setName(((String) tuple[aliasToIndexMap.get("name")]));
                                    tagListDtoTemp.setDescription(((String) tuple[aliasToIndexMap.get("description")]));
                                    tagListDtoTemp.setCountQuestion(((Number) tuple[aliasToIndexMap.get("countquestion")]).longValue());
                                    tagListDtoTemp.setCountQuestionToWeek(((Number) tuple[aliasToIndexMap.get("countquestiontoweek")]).longValue());
                                    tagListDtoTemp.setCountQuestionToDay(((Number) tuple[aliasToIndexMap.get("countquestiontoday")]).longValue());

                                    return tagListDtoTemp;
                                }
                        );
                        return tagListDto1;
                    }

                    @Override
                    public List transformList(List list) {
                        return new ArrayList<>(tagListDtoMap.values());
                    }

                    public  Map<String, Integer> aliasToIndexMap(String[] aliases) {
                         Map<String, Integer> aliasToIndexMap = new LinkedHashMap<>();

                         for (int i = 0; i < aliases.length; i++) {
                             aliasToIndexMap.put(aliases[i], i);
                         }

                         return aliasToIndexMap;
                    }

                    })
                .setFirstResult(page*size-size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public int getTotalResultCountTagDto(){
        long totalResultCount= (long) entityManager.createQuery("select count(tag) from Tag tag").getSingleResult();
        return (int)totalResultCount;
    }
}
