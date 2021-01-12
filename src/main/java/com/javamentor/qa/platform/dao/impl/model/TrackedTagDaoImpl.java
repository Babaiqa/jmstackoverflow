package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.TrackedTagDao;
import com.javamentor.qa.platform.models.entity.question.TrackedTag;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TrackedTagDaoImpl extends ReadWriteDaoImpl<TrackedTag, Long>  implements TrackedTagDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<TrackedTag> getTrackedTagsByUser(String name) {
        return (List<TrackedTag>) entityManager.unwrap(Session.class)
                .createQuery("SELECT trackedTag FROM tag_tracked WHERE tag_tracked.user.name = :name")
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public void addTrackedTag(TrackedTag trackedTag) {
        entityManager.persist(trackedTag);
    }
}
