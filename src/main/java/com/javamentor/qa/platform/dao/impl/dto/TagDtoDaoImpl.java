package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.dto.TagListDto;
import com.javamentor.qa.platform.models.dto.TagRecentDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.*;
import java.time.LocalDateTime;


@Repository
public class TagDtoDaoImpl implements TagDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<TagDto> getTagDtoPagination(int page, int size) {

        return entityManager.createQuery(
                "select new com.javamentor.qa.platform.models.dto.TagDto(tag.id,tag.name)" +
                        " from Tag  tag order by tag.questions.size desc, tag.id ")
                .setFirstResult(page * size - size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public List<TagListDto> getTagDtoPaginationOrderByAlphabet(int page, int size) {

        String query = "Select new com.javamentor.qa.platform.models.dto.TagListDto(t.id, t.name, t.description, " +
                " count(q.id) ," +
                " (select count(q.id) from t.questions q where q.persistDateTime between :stDate1 AND :edDate1 or t.questions.size = 0) ," +
                " (select count(q.id) from t.questions q where q.persistDateTime between :stDate2 AND :edDate2 or t.questions.size = 0))" +
                " from Tag t left join t.questions  q" +
                " group by t.id" +
                " order by t.name";

        LocalDateTime timeNow = LocalDateTime.now();

        return entityManager.createQuery(query)
                .setParameter("stDate1", timeNow.minusDays(7))
                .setParameter("edDate1", timeNow)
                .setParameter("stDate2", timeNow.minusDays(1))
                .setParameter("edDate2", timeNow)
                .setFirstResult(page * size - size)
                .setMaxResults(size)
                .getResultList();

    }

    @Override
    public List<TagListDto> getTagListDtoPaginationOrderByNewTag(int page, int size) {
        return entityManager.createQuery("select new " +
                "com.javamentor.qa.platform.models.dto.TagListDto(tag.id,tag.name) " +
                "from Tag tag order by tag.persistDateTime desc")
                .setFirstResult(page*size-size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public List<TagListDto> getTagListDtoByPopularPagination(int page, int size) {
        return entityManager.createQuery("" +
                "SELECT new com.javamentor.qa.platform.models.dto.TagListDto(t.id, t.name, t.description, COUNT(q.id), " +
                "(SELECT COUNT(q.id) FROM t.questions AS q WHERE q.persistDateTime BETWEEN :startDate1 AND :endDate1), " +
                "(SELECT COUNT(q.id) FROM t.questions AS q WHERE q.persistDateTime BETWEEN :startDate2 AND :endDate2)) " +
                "FROM Tag AS t " +
                "LEFT JOIN t.questions AS q " +
                "GROUP BY t.id " +
                "ORDER BY COUNT(q.id) DESC")
                .setParameter("startDate1", LocalDateTime.now().minusDays(7))
                .setParameter("endDate1", LocalDateTime.now())
                .setParameter("startDate2", LocalDateTime.now().minusDays(1))
                .setParameter("endDate2", LocalDateTime.now())
                .setFirstResult(page * size - size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public List<TagRecentDto> getTagRecentDtoPagination(int page, int size) {
        return entityManager.createQuery("" +
                "SELECT new com.javamentor.qa.platform.models.dto.TagRecentDto(t.id, t.name, " +
                "(SELECT COUNT(q.id) FROM t.questions AS q WHERE q.persistDateTime BETWEEN :start AND :end)) " +
                "FROM Tag AS t " +
                "LEFT JOIN t.questions AS q " +
                "GROUP BY t.id " +
                "ORDER BY COUNT(q.id) DESC")
                .setParameter("start", LocalDateTime.now().minusMonths(1))
                .setParameter("end", LocalDateTime.now())
                .setFirstResult(page * size - size)
                .setMaxResults(size)
                .getResultList();
    }


    @Override
    public int getTotalResultCountTagDto() {
        return (int) (long) entityManager.createQuery("select count(tag) from Tag tag").getSingleResult();
    }

    @Override
    public List<TagListDto> getTagListDtoPagination(int page, int size, String tagName) {
        return entityManager.createQuery("SELECT new com.javamentor.qa.platform.models.dto.TagListDto(e.id, e.name) " +
                "from Tag e where UPPER(e.name) LIKE CONCAT('%',UPPER(:tagName),'%')")
                .setParameter("tagName", tagName)
                .setFirstResult(page*size-size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public int getTotalCountTag(String tagName) {
        return (int) (long) entityManager.createQuery("select count(e) from Tag e where UPPER(e.name) LIKE CONCAT('%',UPPER(:tagName),'%')")
                .setParameter("tagName", tagName)
                .getSingleResult();
    }

    @Override
    public int getTotalResultChildTag(Long id) {
        return (int)(long) entityManager.createQuery("select count(tag.childTag) from RelatedTag tag where tag.mainTag.id=:id")
                .setParameter("id", id)
                .getSingleResult();
    }

    public List<TagRecentDto> getTagRecentDtoChildTagById(int page, int size, Long id) {
        return entityManager
                .createQuery("SELECT new com.javamentor.qa.platform.models.dto.TagRecentDto(t.childTag.id, t.childTag.name, COUNT(t.childTag)) " +
                "FROM RelatedTag t " +
                "GROUP BY t.mainTag, t.childTag, t.childTag.name " +
                "HAVING t.mainTag.id =:id " +
                "ORDER BY COUNT(t.childTag) DESC ")
                .setParameter("id", id)
                .setFirstResult(page * size - size)
                .setMaxResults(size)
                .getResultList();
    }

}
