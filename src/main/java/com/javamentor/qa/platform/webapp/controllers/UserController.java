package com.javamentor.qa.platform.webapp.controllers;

import com.javamentor.qa.platform.dao.impl.model.UserDao;
import com.javamentor.qa.platform.mappers.UserMapper;
import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.models.entity.user.Role;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.impl.model.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/user/")
@Api(value = "UserApi")
public class UserController {

    private final UserService userService;

    private final UserDao userDao;

    @Autowired
    public UserController(UserService userService, UserDao userDao) {
        this.userService = userService;
        this.userDao = userDao;
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

        return id!=null ? ResponseEntity.ok("Swagger work"):
                ResponseEntity.badRequest()
                        .body("Wrong ID");
   }

    @PostMapping(value = "registration", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
           MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        String email = userRegistrationDto.getEmail();
        if (userDao.getUserByEmail(email) != null) {
            String message = "Пользователь с данным email уже существует";
            return ResponseEntity.badRequest().header(message).build();
        } else {
            User user = UserMapper.INSTANCE.userRegistrationDtoToUser(userRegistrationDto);
            Role role = null;
            assert false;
            role.setName("USER");
            user.setRole(role);
            userService.persist(user);
            User us = userDao.getUserByEmail(email);
            UserDto userDto = UserMapper.INSTANCE.userToUserDto(us);
            return ResponseEntity.ok().body(userDto.toString());
        }
   }
}
