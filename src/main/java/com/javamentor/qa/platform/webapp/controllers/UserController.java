package com.javamentor.qa.platform.webapp.controllers;

import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.QuestionDtoService;
import com.javamentor.qa.platform.service.abstracts.dto.TagDtoService;
import com.javamentor.qa.platform.service.abstracts.dto.UserDtoService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("/api/user/")
@Api(value = "UserApi")
public class UserController {

    private UserDtoService userDtoService;

    @Autowired
    public UserController(UserDtoService userDtoService) {
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
          @ApiParam(name="id",value="type Long(or other described)", required = true, example="0")
            @PathVariable Long id){

        return id!=null ? ResponseEntity.ok("Swagger work"):
                ResponseEntity.badRequest()
                        .body("Wrong ID");
   }


   @GetMapping("find")
    @ApiOperation(value = "Return message(Object)", response = String.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returns the object.", response = String.class),
            @ApiResponse(code = 400, message = "There are no users whose name begins with these letters", response = String.class)
    })
    public ResponseEntity<?> getUserListByFirstLetters(@RequestParam("name") String name){

        System.out.println(name);
        Optional<List<UserDto>> listOptional = userDtoService.getUserDtoByName(name);
        return listOptional.isPresent() ? ResponseEntity.ok(listOptional.get()):
                ResponseEntity.badRequest().body("Users not found");

//        ResponseEntity<List<UserDto>> listResponseEntity = new ResponseEntity<List<UserDto>>(userDtoService.getUserDtoByName(name).isPresent(), HttpStatus.OK);
//        return listResponseEntity;


//        HttpEntity<PagedResources<Person>> persons(Pageable pageable,
//                PagedResourcesAssembler assembler) {
//
//            Page<Person> persons = repository.findAll(pageable);
//            return new ResponseEntity<>(assembler.toResources(persons), HttpStatus.OK);

        //Optional<UserDto> userDto = userDtoService.getQuestionDtoById(id);

    }

}
