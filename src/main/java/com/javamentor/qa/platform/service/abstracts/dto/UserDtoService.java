package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.models.dto.UserDtoList;

import java.util.List;
import java.util.Optional;

public interface UserDtoService {

    Optional<UserDto> getUserDtoById(long id);

    List<UserDtoList> getUserDtoByName(String name);

    PageDto<UserDtoList,Object> getPageUserDtoListByName(int page, int size, String name);

}
