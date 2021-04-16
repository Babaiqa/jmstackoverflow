package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.models.entity.question.Question;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuestionDao extends ReadWriteDaoImpl<Question, Long> {
}
