package com.javamentor.qa.platform.webapp.converters.abstracts;

import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.models.entity.user.Role;
import com.javamentor.qa.platform.models.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;


@Mapper(componentModel = "spring")
public abstract  class UserDtoToUserConverter {

    @Mapping( target = "role", constant = "USER", qualifiedByName = "roleName")
    public abstract User userDtoToUser(UserRegistrationDto userRegistrationDto);

    @Named("roleName")
    public Role roleName(String role) {
        return new Role(1L, role);
    }
}
