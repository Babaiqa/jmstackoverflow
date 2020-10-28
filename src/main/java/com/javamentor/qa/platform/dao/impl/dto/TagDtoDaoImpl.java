package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.dto.TagListDto;
import org.hibernate.Session;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Repository;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.*;
import java.time.LocalDateTime;
import java.sql.Timestamp;

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



    @Converter(autoApply = true)
    public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

        @Override
        public Timestamp convertToDatabaseColumn(LocalDateTime locDateTime) {
            return locDateTime == null ? null : Timestamp.valueOf(locDateTime);
        }

        @Override
        public LocalDateTime convertToEntityAttribute(Timestamp sqlTimestamp) {
            return sqlTimestamp == null ? null : sqlTimestamp.toLocalDateTime();
        }
    }

    @Override
    public List<TagListDto> getTagDtoPaginationOrderByAlphabet(int page, int size) {

        String query = "Select t.id as id, t.name as name, t.description as description," +
                " count(q.id) as countquestion," +
                " (select count(q.id) from t.questions q where q.persistDateTime between :stDate1 AND :edDate1 or t.questions.size = 0) as countquestiontoweek," +
                " (select count(q.id) from t.questions q where q.persistDateTime between :stDate2 AND :edDate2 or t.questions.size = 0) as countquestiontoday" +
                " from Tag t left join t.questions  q" +
                " where q.persistDateTime between :stDate1 AND :edDate1" +
                " or t.questions.size = 0" +
                " group by t.id" +
                " order by t.name";

        LocalDateTime timeNow = LocalDateTime.now();

        return entityManager.unwrap(Session.class)
                .createQuery(query)
                .setParameter("stDate1", timeNow.minusDays(7))
                .setParameter("edDate1", timeNow)
                .setParameter("stDate2", timeNow.minusDays(1))
                .setParameter("edDate2", timeNow)
                .unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE)
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
