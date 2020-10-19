package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.models.dto.TagDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TagDtoDAO {

    @PersistenceContext
    private EntityManager entityManager;


    public List<TagDto> getTagDtoPagination(int page, int size) {

        return entityManager.createQuery(
                "select new com.javamentor.qa.platform.models.dto.TagDto(et.id,et.name)" +
                        " from Tag  et order by et.questions.size desc")
                .setFirstResult(page*size-size)
                .setMaxResults(size)
                .getResultList();
    }

}
