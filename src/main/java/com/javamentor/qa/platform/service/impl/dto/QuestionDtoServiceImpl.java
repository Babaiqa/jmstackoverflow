package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.QuestionDtoDao;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.service.abstracts.dto.QuestionDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    @SuppressWarnings("unchecked")
    public PageDto<QuestionDto, Object> getPagination(int page, int size) {
        int totalResultCount = questionDtoDao.getTotalResultCountQuestionDto();

        List<QuestionDto> questionDtoList = questionDtoDao.getPagination(page, size);
        PageDto<QuestionDto, Object> pageDto = new PageDto<>();
        pageDto.setItems(questionDtoList);
        pageDto.setTotalResultCount(totalResultCount);
        pageDto.setCurrentPageNumber(page);
        pageDto.setTotalPageCount((int) Math.ceil(totalResultCount/(double)size));
        pageDto.setItemsOnPage(size);

        return pageDto;
    }

}
