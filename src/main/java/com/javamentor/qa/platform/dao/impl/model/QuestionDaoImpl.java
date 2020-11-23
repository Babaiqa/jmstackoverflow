package com.javamentor.qa.platform.dao.impl.model;


import com.javamentor.qa.platform.dao.abstracts.model.QuestionDao;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;


@Repository
public class QuestionDaoImpl extends ReadWriteDaoImpl<Question, Long> implements QuestionDao {

    private final EntityManager entityManager;

    @Autowired
    public QuestionDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Tag> getAllTagOfQuestion(Question question) {
        return question.getTags();
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public List<Question> getPaginationPopular(int page, int size) {
         return entityManager.createQuery("SELECT q from Question q order by q.viewCount desc ")
                 .setFirstResult(page * size - size)
                 .setMaxResults(size)
                 .getResultList();
    }

}
