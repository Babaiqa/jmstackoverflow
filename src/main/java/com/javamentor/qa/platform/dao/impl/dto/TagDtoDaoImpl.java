package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.dto.TagListDto;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

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
    public List<TagListDto> getTagListDtoByPopularPagination(int page, int size) {
        return entityManager.createQuery("" +
                "SELECT t.id AS id, t.name AS name, COUNT(questions.id) AS countQuestion, " +
                "(SELECT COUNT(q.id) FROM t.questions AS q WHERE q.persistDateTime BETWEEN :startDate1 AND :endDate1) AS countQuestionToWeek, " +
                "(SELECT COUNT(q.id) FROM t.questions AS q WHERE q.persistDateTime BETWEEN :startDate2 AND :endDate2) AS countQuestionToDay " +
                "FROM Tag AS t " +
                "LEFT JOIN t.questions AS questions " +
                "GROUP BY id " +
                "ORDER BY countQuestion DESC")
                .setParameter("startDate1", LocalDateTime.now().minusDays(7))
                .setParameter("endDate1", LocalDateTime.now())
                .setParameter("startDate2", LocalDateTime.now().minusDays(1))
                .setParameter("endDate2", LocalDateTime.now())
                .setFirstResult(page * size - size)
                .setMaxResults(size)
                .unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE)
                .getResultList();
    }


    @Override
    public int getTotalResultCountTagDto() {
        long totalResultCount = (long) entityManager.createQuery("select count(tag) from Tag tag").getSingleResult();
        return (int) totalResultCount;
    }
}
