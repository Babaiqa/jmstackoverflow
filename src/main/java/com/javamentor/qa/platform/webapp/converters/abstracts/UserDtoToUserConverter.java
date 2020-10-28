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

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract  class UserDtoToUserConverter {
    @Autowired
    private  RoleService roleService;

    public abstract UserDto userToDto(User user);

    @Mapping( target = "role", constant = "USER", qualifiedByName = "roleName")
    public abstract User userDtoToUser(UserRegistrationDto userRegistrationDto);

    @Named("roleName")
    public Role roleName(String role) {
        Optional<Role> rol = roleService.getRoleByName(role);
        if (! rol.isPresent()) {
            throw new EntityNotFoundException("Role USER not found");
        }
        return rol.get();
    }
}
