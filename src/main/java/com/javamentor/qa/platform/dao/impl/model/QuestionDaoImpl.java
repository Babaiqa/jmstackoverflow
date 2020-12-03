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

    public long getTotalCountQuestionsWithoutTags(List<Long> tagIds) {
        return (long) entityManager.createQuery("select count (distinct question.id) from Question as question " +
                "where (select count (tag_1) from question.tags as tag_1) = " +
                "(select count (tag_2) from question.tags as tag_2 where tag_2.id not in :tagIds)")
                .setParameter("tagIds", tagIds)
                .getSingleResult();
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public List<Question> getPaginationQuestionsWithoutTags(int page, int size, List<Long> tagIds) {

        return entityManager.createQuery("select distinct question from Question as question " +
                "where (select count (tag_1) from question.tags as tag_1) = " +
                "(select count (tag_2) from question.tags as tag_2 where tag_2.id not in :tagIds)")
                .setParameter("tagIds", tagIds)
                .setFirstResult(page * size - size)
                .setMaxResults(size)
                .getResultList();
    }
}
