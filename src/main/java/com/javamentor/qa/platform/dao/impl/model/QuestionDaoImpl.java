package com.javamentor.qa.platform.dao.impl.model;


import com.javamentor.qa.platform.dao.abstracts.model.QuestionDao;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.Tag;

import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@Repository
public class QuestionDaoImpl extends ReadWriteDaoImpl<Question, Long> implements QuestionDao {


    @Override
    public List<Tag> getAllTagOfQuestion(Question question) {
        return question.getTags();
    }

    @Transactional
    public List<Question> getPaginationPopular(int page, int size) {
        List<Question> list = getAll();

        class CompareQuestionByViewCount implements Comparator<Question>  {
            public int compare(Question q1, Question q2) {
                return q2.getViewCount() - q1.getViewCount();
            }
        }

        list.sort(Collections.reverseOrder(new CompareQuestionByViewCount()));
        if (list.size() < page * size) {
           list.clear();
           return list;
        }else return list.subList(page * size - size, page * size);
    }

}
