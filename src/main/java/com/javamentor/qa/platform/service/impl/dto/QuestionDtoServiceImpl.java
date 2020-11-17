package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.QuestionDtoDao;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.service.abstracts.dto.QuestionDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public PageDto<QuestionDto, Object> getPagination(int page, int size) {
        int totalResultCount = questionDtoDao.getTotalResultCountQuestionDto();

        List<Question> questionList = questionDtoDao.getPagination(page, size);
        List<Long> ids = questionList.stream().map(Question::getId).collect(Collectors.toList());
        List<QuestionDto> questionDtoList = questionDtoDao.getQuestionDtoByTagIds(ids);
        PageDto<QuestionDto, Object> pageDto = new PageDto<>();
        pageDto.setItems(questionDtoList);
        pageDto.setTotalResultCount(totalResultCount);
        pageDto.setCurrentPageNumber(page);
        pageDto.setTotalPageCount((int) Math.ceil(totalResultCount / (double) size));
        pageDto.setItemsOnPage(size);

        return pageDto;
    }

    public PageDto<QuestionDto, Object> getPaginationPopular(int page, int size) {
        int totalResultCount = questionDtoDao.getTotalResultCountQuestionDto();

        List<Question> questionList = questionDtoDao.getPaginationPopular(page, size);
        List<Long> ids = questionList.stream().map(Question::getId).collect(Collectors.toList());
        List<QuestionDto> questionDtoList = questionDtoDao.getQuestionDtoByTagIds(ids);
        PageDto<QuestionDto, Object> pageDto = new PageDto<>();
        pageDto.setItems(questionDtoList);
        pageDto.setTotalResultCount(totalResultCount);
        pageDto.setCurrentPageNumber(page);
        pageDto.setTotalPageCount((int) Math.ceil(totalResultCount / (double) size));
        pageDto.setItemsOnPage(size);

        return pageDto;
    }

}
