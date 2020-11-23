package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.IgnoredTagDao;
import com.javamentor.qa.platform.models.entity.question.IgnoredTag;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.models.entity.user.User;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class IgnoredTagDaoImpl  extends ReadWriteDaoImpl<IgnoredTag, Long>  implements IgnoredTagDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<IgnoredTag> getIgnoredTagsByUser(String name) {
        return (List<IgnoredTag>) entityManager.unwrap(Session.class)
                .createQuery("SELECT ignoredTag FROM tag_ignore WHERE tag_ignore.user.name = :name")
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public void addIgnoredTag(IgnoredTag ignoredTag) {
        entityManager.persist(ignoredTag);
    }
}
