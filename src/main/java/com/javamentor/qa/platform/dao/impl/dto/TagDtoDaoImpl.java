package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.entity.question.Tag;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

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
    public int getTotalResultCountTagDto(){
        long totalResultCount= (long) entityManager.createQuery("select count(tag) from Tag tag").getSingleResult();
        return (int)totalResultCount;
    }

//    @Override
//    public Optional<Tag> getTagByName(String name) {
//        return (Optional<Tag>) entityManager.unwrap(Session.class).createQuery("SELECT tag FROM Tag tag WHERE tag.name = :name")
//                .setParameter("name", name).uniqueResultOptional();
//    }
}
