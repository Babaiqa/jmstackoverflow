package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.models.dto.UserDtoList;

import java.util.Optional;

public interface UserDtoService {

    Optional<UserDto> getUserDtoById(long id);

    PageDto<UserDtoList,Object> getPageUserDtoListByReputationOverPeriod(int page, int size);
}
