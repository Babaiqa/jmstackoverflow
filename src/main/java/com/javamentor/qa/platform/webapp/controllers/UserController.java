package com.javamentor.qa.platform.webapp.controllers;

import com.javamentor.qa.platform.models.dto.*;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.util.OnCreate;
import com.javamentor.qa.platform.service.abstracts.dto.UserDtoService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.webapp.converters.abstracts.UserDtoToUserConverter;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
@Validated
@RequestMapping("/api/user/")
@Api(value = "UserApi")
public class UserController {

    private final UserService userService;
    private final UserDtoToUserConverter userConverter;
    private final UserDtoService userDtoService;
    private static final int MAX_ITEMS_ON_PAGE = 100;

    @Autowired
    public UserController(UserService userService, UserDtoToUserConverter userConverter, UserDtoService userDtoService) {
        this.userService = userService;
        this.userConverter = userConverter;
        this.userDtoService = userDtoService;
    }

    // Examples for Swagger
    @GetMapping("{id}")
    @ApiOperation(value = "Return message(Object)", response = String.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returns the object.", response = String.class),
            @ApiResponse(code = 400, message = "Wrong ID", response = String.class)
    })
    public ResponseEntity<?> getUserById(
            @ApiParam(name = "id", value = "type Long(or other descriped)", required = true, example = "0")
            @PathVariable Long id) {
        Optional<UserDto> userDto = userDtoService.getUserDtoById(id);
        return userDto.isPresent() ? ResponseEntity.ok().body(userDto.get()) :
                ResponseEntity.badRequest()
                        .body("Wrong ID");
    }

    @PostMapping("registration")
    @Validated(OnCreate.class)
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRegistrationDto userRegistrationDto) {
        if (!userService.getUserByEmail(userRegistrationDto.getEmail()).isPresent()) {
            User us = userConverter.userDtoToUser(userRegistrationDto);
            userService.persist(us);
            return ResponseEntity.ok(userConverter.userToDto(us));
        } else {
            return ResponseEntity.badRequest().body("User with email " + userRegistrationDto.getEmail() +
                    " already exist");
        }
    }

    @GetMapping("find")
    @ApiOperation(value = "Return page List<UserDtoList>", response = String.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returns the object.", response = List.class),
            @ApiResponse(code = 400, message = "User with this name does not exist", response = String.class)
    })
    public ResponseEntity<?> getUserListByName(
            @ApiParam(name = "page", value = "Number Page. Type int", required = true, example = "10")
            @RequestParam("page") int page,
            @ApiParam(name = "size", value = "Number of entries per page.Type int. " +
                    "Maximum number of records per page " + MAX_ITEMS_ON_PAGE, required = true,
                    example = "10")
            @RequestParam("size") int size,
            @RequestParam("name") String name) {

        if (userService.getUserByName(name).isPresent()) {
            if (page <= 0 || size <= 0 || size > MAX_ITEMS_ON_PAGE) {
                return ResponseEntity.badRequest().body("The page number and size must be positive. " +
                        "Maximum number of records per page " + MAX_ITEMS_ON_PAGE);
            } else {
                PageDto<UserDtoList, Object> resultPage = userDtoService.getPageUserDtoListByName(page, size, name);
                return ResponseEntity.ok(resultPage);
            }
        } else {
            return ResponseEntity.badRequest().body("User with this name does not exist");
        }
    }
}
