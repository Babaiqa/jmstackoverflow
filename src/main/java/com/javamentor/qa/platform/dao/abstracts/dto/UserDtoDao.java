package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.models.dto.UserDtoList;

import java.util.List;
import java.util.Optional;

public interface UserDtoDao {
    Optional<UserDto> getUserById(long id);

    List<UserDtoList> getPageUserDtoListByReputationOverPeriod(int page, int size, int quantityOfDay);

    public int getTotalResultCountUsers();
    Optional<UserDtoList> getUserDtoByName(String name);
}
