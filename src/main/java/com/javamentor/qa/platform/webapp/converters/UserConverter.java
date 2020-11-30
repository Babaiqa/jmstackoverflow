package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.models.dto.UserPublicInfoDto;
import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.models.entity.user.Role;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.UserDtoService;
import com.javamentor.qa.platform.service.abstracts.model.RoleService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class UserConverter {

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

    @Mapping(source = "userPublicInfoDto.nickname", target = "nickname")
    @Mapping(source = "userPublicInfoDto.about", target = "about")
    @Mapping(source = "userPublicInfoDto.linkImage", target = "imageLink")
    @Mapping(source = "userPublicInfoDto.linkSite", target = "linkSite")
    @Mapping(source = "userPublicInfoDto.linkVk", target = "linkVk")
    @Mapping(source = "userPublicInfoDto.linkGitHub", target = "linkGitHub")
    @Mapping(source = "userPublicInfoDto.fullName", target = "fullName")
    @Mapping(source = "userPublicInfoDto.city", target = "city")

    public abstract User userPublicInfoDtoToUser(UserPublicInfoDto userPublicInfoDto);
}
