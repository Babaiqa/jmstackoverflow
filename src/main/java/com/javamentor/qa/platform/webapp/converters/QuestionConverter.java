package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.user.Role;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.RoleService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class QuestionConverter {


    @Autowired
    private UserService userService;

    @Mapping(source = "question.id", target = "id")
    @Mapping(source = "question.user.id", target = "authorId")
    @Mapping(source = "question.title", target = "title")
    @Mapping(source = "question.description", target = "description")
    @Mapping(source = "question.user.fullName", target = "authorName")
    @Mapping(source = "question.user.imageLink", target = "authorImage")
    @Mapping(source = "question.viewCount", target = "viewCount")
    @Mapping(source = "question.tags", target = "listTagDto")
    @Mapping(source = "question.persistDateTime", target = "persistDateTime")
    @Mapping(source = "question.lastUpdateDateTime", target = "lastUpdateDateTime")

    public abstract QuestionDto questionToQuestionDto(Question question);



    @Mapping(target = "id", source = "questionDto.id")
    @Mapping(target = "user", constant = "authorId", qualifiedByName = "userEntity")
    @Mapping(target = "title", source = "questionDto.title")
    @Mapping(target = "description",source = "questionDto.description")
    @Mapping(target = "user.fullName", source = "questionDto.authorName")
    @Mapping(target = "user.imageLink", source = "questionDto.authorImage")
    @Mapping(target = "viewCount", source = "questionDto.viewCount")
    @Mapping(target = "tags", source = "questionDto.listTagDto")
    @Mapping(target = "persistDateTime", source = "questionDto.persistDateTime")
    @Mapping(target = "lastUpdateDateTime", source = "questionDto.lastUpdateDateTime")

    public abstract Question questionDtoToQuestion(QuestionDto questionDto);


    @Named("userEntity")
    public User userEntity(Long user) {
        Optional<User> use = userService.getById(user);
        if (! use.isPresent()) {
            throw new EntityNotFoundException("User not found");
        }
        return use.get();
    }




//    @Mapping( target = "role", constant = "USER", qualifiedByName = "roleName")
//    public abstract User userDtoToUser(UserRegistrationDto userRegistrationDto);
//
//    @Named("roleName")
//    public Role roleName(String role) {
//        Optional<Role> rol = roleService.getRoleByName(role);
//        if (! rol.isPresent()) {
//            throw new EntityNotFoundException("Role USER not found");
//        }
//        return rol.get();
//    }



}
