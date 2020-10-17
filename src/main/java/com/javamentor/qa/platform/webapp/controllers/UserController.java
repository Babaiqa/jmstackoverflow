package com.javamentor.qa.platform.webapp.controllers;

import com.javamentor.qa.platform.mappers.UserMapper;
import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.util.OnCreate;
import com.javamentor.qa.platform.service.impl.model.UserService;
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

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
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
        if (!userService.getById(id).isPresent()) {
            return ResponseEntity.badRequest().body("User with id " + id + " not found");
        } else {
            User user = userService.getById(id).get();
            return ResponseEntity.ok().body(userMapper.userToUserDto(user).toString());
        }
//        return id!=null ? ResponseEntity.ok("Swagger work"):
//                ResponseEntity.badRequest()
//                        .body("Wrong ID");
   }

    @PostMapping(value = "registration")
    @Validated(OnCreate.class)
    public ResponseEntity<String> createUser(@Valid @RequestBody UserRegistrationDto userRegistrationDto) {
        if (!userService.getByEmail(userRegistrationDto.getEmail()).isPresent()) {
            User user  = userMapper.userRegistrationDtoToUser(userRegistrationDto);
            userService.persist(user);
            return ResponseEntity.ok().body(userMapper.userToUserDto(user).toString());
        } else {
            return ResponseEntity.badRequest().body("User with email " + userRegistrationDto.getEmail() + " already exist");
        }
    }

}
