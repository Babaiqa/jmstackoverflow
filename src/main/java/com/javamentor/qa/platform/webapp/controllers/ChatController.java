package com.javamentor.qa.platform.webapp.controllers;

import com.javamentor.qa.platform.models.dto.SingleChatDto;
import com.javamentor.qa.platform.service.abstracts.dto.SingleChatDtoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/chat")
@Api(value = "ChatApi")
public class ChatController {

    private SingleChatDtoService singleChatDtoService;

    @Autowired
    public ChatController(SingleChatDtoService singleChatDtoService) {
        this.singleChatDtoService = singleChatDtoService;
    }


    @GetMapping(path = "/single")
    @ApiOperation(value = "Return object(List<SingleChatDto>)")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returns the all SingleChat List<SingleChatDto>"),
    })
    public ResponseEntity<?> getSingleChats() {

        List<SingleChatDto> allSingleChats = singleChatDtoService.getAllSingleChatDto();

        return ResponseEntity.ok(allSingleChats);

    }

}
