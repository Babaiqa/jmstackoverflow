package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.QuestionDtoDao;
import com.javamentor.qa.platform.dao.impl.model.QuestionDaoImpl;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.service.abstracts.dto.QuestionDtoService;
import com.javamentor.qa.platform.service.abstracts.pagination.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class QuestionDtoServiceImpl implements QuestionDtoService {

    private final QuestionDtoDao questionDtoDao;
    private QuestionDaoImpl questionDao;
    private final PaginationService<QuestionDto, Object> paginationService;

    @Autowired
    public QuestionDtoServiceImpl(QuestionDtoDao questionDtoDao,
                                  QuestionDaoImpl questionDao,
                                  PaginationService<QuestionDto, Object> paginationService) {

        this.questionDtoDao = questionDtoDao;
        this.questionDao = questionDao;
        this.paginationService = paginationService;
    }

    @Transactional
    public Optional<QuestionDto> getQuestionDtoById(Long id) {
        return questionDtoDao.getQuestionDtoById(id);
    }

    public PageDto<QuestionDto, Object> getPagination(int page, int size) {
        return paginationService.getPageDto(
                "paginationQuestion",
                setPaginationParameters(page, size, Optional.empty(), Optional.empty()));
    }

    public PageDto<QuestionDto, Object> getPaginationPopular(int page, int size) {
        return paginationService.getPageDto(
                "paginationQuestionByPopular",
                setPaginationParameters(page, size, Optional.empty(), Optional.empty()));
    }

    public PageDto<QuestionDto, Object> getPaginationOrderedNew(int page, int size) {
        return paginationService.getPageDto(
                "paginationQuestionOrderByNew",
                setPaginationParameters(page, size, Optional.empty(), Optional.empty()));
    }

    public PageDto<QuestionDto, Object> getPaginationWithoutAnswers(int page, int size) {
        return paginationService.getPageDto(
                "paginationQuestionWithoutAnswers",
                setPaginationParameters(page, size, Optional.empty(), Optional.empty()));
    }

    public PageDto<QuestionDto, Object> getPAginationWithGivenTags(int page, int size, List<Long> tagIds) {
        return paginationService.getPageDto(
                "paginationQuestionWithGivenTags",
                setPaginationParameters(page, size, Optional.ofNullable(tagIds), Optional.empty()));
    }

    public PageDto<QuestionDto, Object> getPaginationWithoutTags(int page, int size, List<Long> tagIds) {
        return paginationService.getPageDto(
                "paginationQuestionWithoutTags",
                setPaginationParameters(page, size, Optional.ofNullable(tagIds), Optional.empty()));
    }

    @Override
    public PageDto<QuestionDto, Object> getQuestionBySearchValue(String message, int page, int size) {
        return paginationService.getPageDto(
                "paginationQuestionBySearchValue",
                setPaginationParameters(page, size, Optional.empty(), Optional.ofNullable(message)));
    }

    private Map<String, Object> setPaginationParameters(int page, int size, Optional<List<Long>> tagsIds, Optional<String> message) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("page", page);
        parameters.put("size", size);
        parameters.put("tagsIds", tagsIds.orElse(new ArrayList<>()));
        parameters.put("message", message.orElse(""));
        return parameters;
    }

}