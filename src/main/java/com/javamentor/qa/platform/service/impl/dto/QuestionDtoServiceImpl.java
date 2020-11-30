package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.QuestionDtoDao;
import com.javamentor.qa.platform.dao.impl.model.QuestionDaoImpl;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.service.abstracts.dto.QuestionDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuestionDtoServiceImpl implements QuestionDtoService {

    private final QuestionDtoDao questionDtoDao;
    private QuestionDaoImpl questionDao;

    @Autowired
    public QuestionDtoServiceImpl(QuestionDtoDao questionDtoDao, QuestionDaoImpl questionDao) {
        this.questionDtoDao = questionDtoDao;
        this.questionDao = questionDao;
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


        List<Question> questionList = questionDao.getPaginationPopular(page, size);
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

    public PageDto<QuestionDto, Object> getPaginationOrderedNew(int page, int size) {
        int totalResultCount = questionDtoDao.getTotalResultCountQuestionDto();


        List<QuestionDto> questionList = questionDtoDao.getPaginationOrderedNew(page, size);
        List<Long> ids = questionList.stream().map(QuestionDto::getId).collect(Collectors.toList());

        List<QuestionDto> tegsByIds = questionDtoDao.getQuestionTagsByQuestionIds(ids);

        for (QuestionDto q : questionList) {
            for (QuestionDto q2 : tegsByIds) {
                if (q.getId().equals(q2.getId())) {
                    q.setListTagDto(q2.getListTagDto());
                }
            }
        }

        PageDto<QuestionDto, Object> pageDto = new PageDto<>();
        pageDto.setItems(questionList);
        pageDto.setTotalResultCount(totalResultCount);
        pageDto.setCurrentPageNumber(page);
        pageDto.setTotalPageCount((int) Math.ceil(totalResultCount / (double) size));
        pageDto.setItemsOnPage(size);

        return pageDto;
    }

    public PageDto<QuestionDto, Object> getPaginationWithoutAnswers(int page, int size) {
        int maxResult = (int) questionDtoDao.getTotalCountQuestionNotAnswer();
        List<Long> noAnsweredQuestionsIDs = questionDtoDao.getQuestionsNotAnsweredIDs(page, size);

        List<QuestionDto> questionDtoList = questionDtoDao.getQuestionDtoByIds(noAnsweredQuestionsIDs);
        PageDto<QuestionDto, Object> pageDto = new PageDto<>();
        pageDto.setItems(questionDtoList);
        pageDto.setTotalResultCount(maxResult);
        pageDto.setCurrentPageNumber(page);
        pageDto.setTotalPageCount((int) Math.ceil(maxResult / (double) size));
        pageDto.setItemsOnPage(size);

        return pageDto;
    }

    @Override
    public PageDto<QuestionDto, Object> getQuestionBySearchValue(String message, int page, int size) {
        Map<String, String> searchParams = new HashMap<>();
        searchParams.put("views", parseSearchParam(message, "views"));
        searchParams.put("author", parseSearchParam(message, "author"));
        searchParams.put("answers", parseSearchParam(message, "answers"));
        searchParams.put("tags", parseSearchExactly(message, true));
        searchParams.put("exactSearch", parseSearchExactly(message, false));
        searchParams.put("textSearch", message
                .replaceAll("(\"[^\"]+\")|(\\[[^\\]\\[]+\\])|([\\]\\[])|(answers:[\\s]?\\d+)|(author:[\\s]?\\d+)|(views:[\\s]?\\d+)", "")
                .replaceAll("\\s{2,}", " ")
                .trim());

//        int size = 10;
//        int page = 2;

        List<QuestionDto> totalResult = questionDtoDao.getQuestionBySearchValue(searchParams);
        List<QuestionDto> paginationResult =
                totalResult.subList((page - 1) * size, (size * page) > totalResult.size()? totalResult.size(): size * page);

        PageDto<QuestionDto, Object> pagination = new PageDto<>();
        pagination.setItems(paginationResult);
        pagination.setTotalResultCount(totalResult.size());
        pagination.setCurrentPageNumber(page);
        pagination.setTotalPageCount((int) Math.ceil(totalResult.size() / (double) size));
        pagination.setItemsOnPage(size);
        return pagination;
    }

    private String parseSearchExactly(String message, boolean isTag) {
        StringBuilder exactly = new StringBuilder();
        while (message.matches("(.*\".+\".*)|(.*\\[.+\\].*)")) {
            int firstIndex = message.indexOf(isTag ? "[" : "\"");
            int secondIndex = message.indexOf(isTag ? "]" : "\"", firstIndex + 1) + 1;
            if (firstIndex < 0 || secondIndex < firstIndex) {
                break;
            }
            String tmp = message.substring(firstIndex, secondIndex);
            message = message.replace(tmp, "");
            exactly.append(tmp.substring(1, tmp.length() - 1)).append(" ");
        }
        return exactly.toString().trim();
    }

    private String parseSearchParam(String message, String param) {
        if (message.matches(".*" + param + ":\\d+.*")) {
            int index = message.indexOf(param + ":");
            String results = message.substring(index, index + param.length() + 1);

            for (int i = index + param.length() + 1; i < message.length(); i++) {
                char c = message.charAt(i);
                if (Character.isDigit(c)) {
                    results += c;
                } else {
                    break;
                }
            }
            if (results.length() > param.length() + 1) {
                return results.substring(param.length() + 1);
            }
        }
        return "";
    }

}
