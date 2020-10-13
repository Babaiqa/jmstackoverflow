package com.javamentor.qa.platform.webapp.controllers;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {


    // Examples for Swagger
    @GetMapping("/api/user/{id}")
    @ApiOperation(value = "Return message. If id=null badRequest ")
   public  ResponseEntity<String> getUserById(@PathVariable Long id){

        return id!=null? ResponseEntity.ok("Swager work"):
                ResponseEntity.badRequest()
                        .body("Wrong ID");
   }





}
