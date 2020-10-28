package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.dto.TagRecentDto;
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
    public List<TagRecentDto> getTagRecentDtoPagination(int page, int size) {
        return entityManager.createQuery("" +
                "SELECT t.id AS id, t.name AS name, COUNT(q.id) AS countTagToQuestion " +
                "FROM Tag AS t " +
                "LEFT JOIN t.questions AS q " +
                "WHERE q.persistDateTime BETWEEN :start AND :end " +
                "GROUP BY id " +
                "ORDER BY countTagToQuestion DESC")
                .setParameter("start", LocalDateTime.now().minusMonths(1))
                .setParameter("end", LocalDateTime.now())
                .setFirstResult(page * size - size)
                .setMaxResults(size)
                .unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE)
                .getResultList();
    }

    @Override
    public int getTotalResultCountTagDto() {
        long totalResultCount = (long) entityManager.createQuery("SELECT COUNT(tag) FROM Tag tag")
                .getSingleResult();
        return (int) totalResultCount;
    }
}
