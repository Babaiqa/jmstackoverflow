package com.javamentor.qa.platform.webapp.converters.abstracts;

import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.models.entity.user.Role;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.RoleService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract  class UserDtoToUserConverter {
    @Autowired
    private  RoleService roleService;

    public abstract UserDto userToDto(User user);

    @Mapping( target = "role", constant = "USER", qualifiedByName = "roleName")
    public abstract User userDtoToUser(UserRegistrationDto userRegistrationDto);

    @Named("roleName")
    public Role roleName(String role) {
        return roleService.getRoleByName(role).orElse(new Role(1L, role));
    }
}
