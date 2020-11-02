package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.models.dto.UserDtoList;

import java.util.List;
import java.util.Optional;

public interface UserDtoDao {
    Optional<UserDto> getUserById(long id);

    public int getTotalResultCountUsers();

    List<UserDtoList> getUserDtoByName(String name);

    List<UserDtoList> getPageUserDtoListByName(int page, int size, String name);
}
