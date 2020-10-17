package com.javamentor.qa.platform.mappers;

import com.javamentor.qa.platform.mappers.abstracts.UserMapperDto;
import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.models.entity.user.Role;
import com.javamentor.qa.platform.models.entity.user.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public class UserMapperImpl extends UserMapperDto {
    @Override
    public User userRegistrationDtoToUser(UserRegistrationDto userRegistrationDto) {

        Role role = new Role();
        role.setName("USER");
        User user = new User();
        user.setFullName(userRegistrationDto.fullName);
        user.setEmail(userRegistrationDto.email);
        user.setPassword(userRegistrationDto.password);
        user.setRole(role);

        return user;
    }

    @Override
    public UserDto userToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFullName(user.getFullName());
        userDto.setEmail(user.getEmail());
        userDto.setLinkImage("/img/no_image.jpg");
        userDto.setReputation(1L);

        return userDto;
    }
}
