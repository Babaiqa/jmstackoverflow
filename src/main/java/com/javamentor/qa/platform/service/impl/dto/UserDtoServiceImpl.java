package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.UserDtoDao;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.models.dto.UserDtoList;
import com.javamentor.qa.platform.service.abstracts.dto.UserDtoService;
import com.javamentor.qa.platform.service.abstracts.dto.pagination.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserDtoServiceImpl implements UserDtoService {

    private final UserDtoDao userDtoDao;
    private final PaginationService<UserDtoList, Object> paginationService;
    private final QuestionDtoServiceImpl questionDtoService;

    @Autowired
    public UserDtoServiceImpl(UserDtoDao userDtoDao, PaginationService<UserDtoList, Object> paginationService, QuestionDtoServiceImpl questionDtoService) {
        this.userDtoDao = userDtoDao;
        this.paginationService = paginationService;
        this.questionDtoService = questionDtoService;
    }

    @Override
    public Optional<UserDto> getUserDtoById(long id) {
        return userDtoDao.getUserById(id);
    }

    @Override
    public PageDto<UserDtoList, Object> getPageUserDtoListByReputationOverAllTime(int page, int size) {
        return paginationService.getPageDto(
                "paginationUserByReputationOverPeriod",
                setPaginationParameters(page, size, Optional.empty(), Optional.of(3650)));
    }

    @Override
    public PageDto<UserDtoList, Object> getPageUserDtoListByReputationOverYear(int page, int size) {
        return paginationService.getPageDto(
                "paginationUserByReputationOverPeriod",
                setPaginationParameters(page, size, Optional.empty(), Optional.of(365)));
    }

    @Override
    public PageDto<UserDtoList, Object> getPageUserDtoListByReputationOverQuarter(int page, int size) {
        return paginationService.getPageDto(
                "paginationUserByReputationOverPeriod",
                setPaginationParameters(page, size, Optional.empty(), Optional.of(120)));
    }

    @Override
    public PageDto<UserDtoList, Object> getPageUserDtoListByReputationOverMonth(int page, int size) {
        return paginationService.getPageDto(
                "paginationUserByReputationOverPeriod",
                setPaginationParameters(page, size, Optional.empty(), Optional.of(30)));
    }

    @Override
    public PageDto<UserDtoList, Object> getPageUserDtoListByReputationOverWeek(int page, int size) {
        return paginationService.getPageDto(
                "paginationUserByReputationOverPeriod",
                setPaginationParameters(page, size, Optional.empty(), Optional.of(7)));
    }

    @Override
    public PageDto<UserDtoList, Object> getPageUserDtoListByReputation(int page, int size) {
        return paginationService.getPageDto(
                "paginationUserByReputation",
                setPaginationParameters(page, size, Optional.empty(), Optional.empty()));
    }



    @Override
    public PageDto<UserDtoList, Object> getPageUserDtoListByName(int page, int size, String name) {
        return paginationService.getPageDto(
                "paginationUserReputationByName",
                setPaginationParameters(page, size, Optional.ofNullable(name), Optional.empty()));
    }

    @Override
    public PageDto<QuestionDto, Object> getUserQuestionsSortedByVotes(int page, int size, long id) {
        Map<String, Object> parameters = setPaginationParameters(page, size, Optional.empty(), Optional.empty());
        parameters.put("page", page);
        parameters.put("size", size);
        parameters.put("userId", id);
        return questionDtoService.getPageDto("userQuestionsOrderedByVotes", parameters);
    }

    private Map<String, Object> setPaginationParameters(int page, int size, Optional<String> name, Optional<Integer> days) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("page", page);
        parameters.put("size", size);
        parameters.put("name", name.orElse(""));
        parameters.put("days", days.orElse(30));
        return parameters;
    }

}
