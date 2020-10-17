package com.javamentor.qa.platform.mappers;

import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.models.entity.user.User;


public interface UserMapper {

    User userRegistrationDtoToUser(UserRegistrationDto userRegistrationDto);

    UserDto userToUserDto(User user);
}
