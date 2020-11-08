package com.javamentor.qa.platform.webapp.controllers;

import com.javamentor.qa.platform.models.dto.*;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.util.OnCreate;
import com.javamentor.qa.platform.service.abstracts.dto.UserDtoService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.webapp.converters.UserConverter;
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
    private final UserConverter userConverter;
    private final UserDtoService userDtoService;
    private static final int MAX_ITEMS_ON_PAGE = 100;

    @Autowired
    public UserController(UserService userService, UserConverter userConverter, UserDtoService userDtoService) {
        this.userService = userService;
        this.userConverter = userConverter;
        this.userDtoService = userDtoService;
    }

    // Examples for Swagger
    @GetMapping("{id}")
    @ApiOperation(value = "Return message(Object)", response = String.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returns the object.", response = UserDto.class),
            @ApiResponse(code = 400, message = "Wrong ID",response = String.class)
    })
    public  ResponseEntity<?> getUserById(
        @ApiParam(name="id",value="type Long(or other descriped)", required = true, example="0")
        @PathVariable Long id){
        Optional<UserDto> userDto = userDtoService.getUserDtoById(id);
        return userDto.isPresent() ? ResponseEntity.ok().body(userDto.get()):
                ResponseEntity.badRequest()
                        .body("User with id " + id + " not found");

   }

    @PostMapping("registration")
    @Validated(OnCreate.class)
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRegistrationDto userRegistrationDto ) {
        if (!userService.getUserByEmail(userRegistrationDto.getEmail()).isPresent()) {
            User us = userConverter.userDtoToUser(userRegistrationDto);
            userService.persist(us);
            return ResponseEntity.ok(userConverter.userToDto(us));
        } else {
            return ResponseEntity.badRequest().body("User with email " + userRegistrationDto.getEmail() +
                    " already exist");
        }
    }



    @GetMapping("order/reputation/week")
    @ApiOperation(value = "Get page List<UserDtoList> order by reputation over week." +
            "UserDtoList contains List<TagDto> with size 3 " +
            "and order by activity. Max size entries on page= "+ MAX_ITEMS_ON_PAGE, response = List.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returns the pagination List<UserDtoList> order by reputation over week",
                    response = List.class),
            @ApiResponse(code = 400, message = "Wrong value of size or page", response = String.class)
    })
    public ResponseEntity<?> getUserDtoListPaginationByReputationOverWeek(
            @ApiParam(name = "page", value = "Number Page. Type int", required = true, example = "10")
            @RequestParam("page") int page,
            @ApiParam(name = "size", value = "Number of records per page.Type int." +
                    "Maximum number of records per page"+ MAX_ITEMS_ON_PAGE , required = true,
                    example = "10")
            @RequestParam("size") int size) {

        if (page <= 0 || size <= 0 || size > MAX_ITEMS_ON_PAGE) {
            return ResponseEntity.badRequest().body("Номер страницы и размер должны быть " +
                    "положительными. Максимальное количество записей на странице " + MAX_ITEMS_ON_PAGE);
        }
        PageDto<UserDtoList,Object> resultPage = userDtoService.getPageUserDtoListByReputationOverWeek(page, size);

        return  ResponseEntity.ok(resultPage);
    }






}
