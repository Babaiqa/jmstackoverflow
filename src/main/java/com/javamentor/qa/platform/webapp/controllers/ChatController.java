package com.javamentor.qa.platform.webapp.controllers;

import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.SingleChatDto;
import com.javamentor.qa.platform.models.entity.chat.Message;
import com.javamentor.qa.platform.service.abstracts.dto.SingleChatDtoService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Validated
@RequestMapping("/api/chat")
@Api(value = "ChatApi")
public class ChatController {

    private SingleChatDtoService singleChatDtoService;

    private static final int MAX_ITEMS_ON_PAGE = 100;

    @Autowired
    public ChatController(SingleChatDtoService singleChatDtoService) {
        this.singleChatDtoService = singleChatDtoService;
    }


    @GetMapping(path = "/single")
    @ApiOperation(value = "Get page SingleChatDto. MAX ITEMS ON PAGE=" + MAX_ITEMS_ON_PAGE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returns the pagination List<SingleChatDto>"),
    })
    public ResponseEntity<?> getAllSingleChatPagination(
            @ApiParam(name = "page", value = "Number Page. type int", required = true, example = "1")
            @RequestParam("page") int page,
            @ApiParam(name = "size", value = "Number of entries per page.Type int." +
                    " Максимальное количество записей на странице " + MAX_ITEMS_ON_PAGE,
                    example = "10")
            @RequestParam("size") int size
    ) {

        if (page <= 0 || size <= 0 || size > MAX_ITEMS_ON_PAGE) {
            return ResponseEntity.badRequest().body("Номер страницы и размер должны быть " +
                    "положительными. Максимальное количество записей на странице " + MAX_ITEMS_ON_PAGE);
        }

        PageDto<SingleChatDto, Object> allSingleChats = singleChatDtoService.getAllSingleChatDtoPagination(page, size);

        return ResponseEntity.ok(allSingleChats);
    }

    @MessageMapping("/message")
    @SendTo("/chat/messages")
    public Message getMessages(Message message) {
        return message;
    }

}
