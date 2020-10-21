package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.QuestionDtoDao;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.service.abstracts.dto.QuestionDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class QuestionDtoServiceImpl implements QuestionDtoService {

    private final QuestionDtoDao questionDtoDao;

    @Autowired
    public QuestionDtoServiceImpl(QuestionDtoDao questionDtoDao) {
        this.questionDtoDao = questionDtoDao;
    }

    @Transactional
    public Optional<QuestionDto> getQuestionDtoById(Long id) {
        return questionDtoDao.getQuestionDtoById(id);
    }

}
