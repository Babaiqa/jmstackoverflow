package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.models.dto.TagRecentDto;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TagDtoDaoImpl implements TagDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<TagRecentDto> getTagRecentDtoPagination(int page, int size) {
        return entityManager.createNativeQuery(
                "SELECT id, name, coalesce(COUNT(tag_id), 0) AS countTagToQuestion " +
                        "FROM tag " +
                        "LEFT JOIN question_has_tag ON tag.id = question_has_tag.tag_id " +
                        "WHERE persist_date >= date(now() - interval '1 month') AND persist_date <= date(now()) " +
                        "GROUP BY id " +
                        "ORDER BY countTagToQuestion desc")
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
