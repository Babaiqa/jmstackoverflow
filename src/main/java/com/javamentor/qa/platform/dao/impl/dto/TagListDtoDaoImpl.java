package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagListDtoDao;
import com.javamentor.qa.platform.models.dto.TagListDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TagListDtoDaoImpl implements TagListDtoDao {

    @PersistenceContext
    private EntityManager entityManager;



    @Override
    public List<TagListDto> getTagListDtoPaginationOrderByNewTag(int page, int size) {

        return entityManager.createQuery("select new " +
                "com.javamentor.qa.platform.models.dto.TagListDto(tag.id,tag.name,tag.description) " +
                "from Tag tag order by tag.persistDateTime")
                .setFirstResult(page*size-size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public int getTotalResultCountTagListDto() {
        long totalResultCount = (long) entityManager.createQuery("select count(tag) from Tag tag").getSingleResult();

        return (int) totalResultCount;
    }


}
