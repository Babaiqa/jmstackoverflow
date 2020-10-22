package com.javamentor.qa.platform.webapp.controllers;

import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.models.entity.user.User;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("/api/user/")
@Api(value = "UserApi")
public class UserController {


    // Examples for Swagger
    @GetMapping("{id}")
    @ApiOperation(value = "Return message(Object)", response = String.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returns the object.", response = String.class),
            @ApiResponse(code = 400, message = "Wrong ID",response = String.class)
    })
    public  ResponseEntity<String> getUserById(
          @ApiParam(name="id",value="type Long(or other described)", required = true, example="0")
            @PathVariable Long id){

        return id!=null ? ResponseEntity.ok("Swagger work"):
                ResponseEntity.badRequest()
                        .body("Wrong ID");
   }

//    @GetMapping("find?name={firstLettersFullName}")
//    @ApiOperation(value = "Return message(Object)", response = String.class)
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "Returns the object.", response = String.class),
//            @ApiResponse(code = 400, message = "There are no users whose name begins with these letters", response = String.class)
//    })
//    public ResponseEntity<List<UserDto>> getUserListByFirstLetters(){
//        //Optional<UserDto> userDto = userDtoService.getQuestionDtoById(id);
//    }

}
