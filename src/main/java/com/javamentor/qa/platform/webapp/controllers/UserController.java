package com.javamentor.qa.platform.webapp.controllers;


import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.util.OnCreate;
import com.javamentor.qa.platform.service.abstracts.dto.UserDtoService;
import com.javamentor.qa.platform.service.impl.model.UserServiceImpl;
import com.javamentor.qa.platform.webapp.converters.abstracts.UserDtoToUserConverter;
import com.javamentor.qa.platform.webapp.converters.abstracts.UserToDtoConverter;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
@RequestMapping("/api/user/")
@Api(value = "UserApi")
public class UserController {

    private final UserServiceImpl userService;
    private final UserDtoToUserConverter userConverter;
    private final UserToDtoConverter userToDtoConverter;
    private final UserDtoService userDtoService;

    @Autowired
    public UserController(UserServiceImpl userService, UserDtoToUserConverter userConverter, UserToDtoConverter userToDtoConverter, UserDtoService userDtoService) {
        this.userService = userService;
        this.userConverter = userConverter;
        this.userToDtoConverter = userToDtoConverter;
        this.userDtoService = userDtoService;
    }

    // Examples for Swagger
    @GetMapping("{id}")
    @ApiOperation(value = "Return message(Object)", response = String.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returns the object.", response = String.class),
            @ApiResponse(code = 400, message = "Wrong ID",response = String.class)
    })
    public  ResponseEntity<String> getUserById(
        @ApiParam(name="id",value="type Long(or other descriped)", required = true, example="0")
        @PathVariable Long id){
        UserDto userDto = userDtoService.getUserDtoById(id);
//        if (!userService.getById(id).isPresent()) {
//            return ResponseEntity.badRequest().body("User with id " + id + " not found");
//        } else {
//            User user = userService.getById(id).get();
//            return ResponseEntity.ok().body(userToDtoConverter.userToDto(user).toString());
//        }
        return id!=null ? ResponseEntity.ok().body(userDto.toString()):
                ResponseEntity.badRequest()
                        .body("Wrong ID");
   }

    @PostMapping("registration")
    @Validated(OnCreate.class)
    public ResponseEntity<String> createUser(@Valid @RequestBody UserRegistrationDto userRegistrationDto) {
        if (!userService.getUserByEmail(userRegistrationDto.getEmail()).isPresent()) {
            User us = userConverter.userDtoToUser(userRegistrationDto);
            userService.persist(us);
            UserDto userDto = userToDtoConverter.userToDto(us);
            return ResponseEntity.ok().body(userDto.toString());
        } else {
            return ResponseEntity.badRequest().body("User with email " + userRegistrationDto.getEmail() + " already exist");
        }
    }

}
