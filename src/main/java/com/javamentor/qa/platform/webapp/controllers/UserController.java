package com.javamentor.qa.platform.webapp.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/user/")
public class UserController {


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

        return id!=null ? ResponseEntity.ok("Swager work"):
                ResponseEntity.badRequest()
                        .body("Wrong ID");
   }
}
