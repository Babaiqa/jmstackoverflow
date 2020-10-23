package com.javamentor.qa.platform.webapp.converters.abstracts;

import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.models.entity.user.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = UserDto.class)
public abstract class UserToDtoConverter {
    public abstract UserDto userToDto(User user);
}
