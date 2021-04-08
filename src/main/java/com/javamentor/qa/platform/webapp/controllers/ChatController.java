package com.javamentor.qa.platform.webapp.controllers;

import com.javamentor.qa.platform.models.dto.MessageDto;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.SingleChatDto;
import com.javamentor.qa.platform.service.abstracts.dto.MessageDtoService;
import com.javamentor.qa.platform.models.entity.chat.Message;
import com.javamentor.qa.platform.service.abstracts.dto.SingleChatDtoService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/chat")
@Api(value = "ChatApi")
public class ChatController {

    private final SingleChatDtoService singleChatDtoService;
    private final MessageDtoService messageDtoService;

    private static final int MAX_ITEMS_ON_PAGE = 100;

    @Autowired
    public ChatController(SingleChatDtoService singleChatDtoService, MessageDtoService messageDtoService) {
        this.singleChatDtoService = singleChatDtoService;
        this.messageDtoService = messageDtoService;
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

    @GetMapping(path = "/{chatId}/message")
    @ApiOperation(value = "Get page MessageDto. MAX ITEMS ON PAGE=" + MAX_ITEMS_ON_PAGE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returns the pagination List<MessageDto> "),
    })
    public ResponseEntity<?> getAllMessageByChatIdPagination(
            @ApiParam(name = "page", value = "Number Page. type int", required = true, example = "1")
            @RequestParam("page") int page,
            @ApiParam(name = "size", value = "Number of entries per page.Type int." +
                    " Максимальное количество записей на странице " + MAX_ITEMS_ON_PAGE,
                    example = "10")
            @RequestParam("size") int size,

            @PathVariable("chatId") long chatId
    ) {
        if (page <= 0 || size <= 0 || size > MAX_ITEMS_ON_PAGE) {
            return ResponseEntity.badRequest().body("Номер страницы и размер должны быть " +
                    "положительными. Максимальное количество записей на странице " + MAX_ITEMS_ON_PAGE);
        }

        PageDto<MessageDto, Object> allMessage = messageDtoService.getAllMessageDtoByChatIdPagination(page, size, chatId);


        return ResponseEntity.ok(allMessage);
    }

    @MessageMapping("/message")
    @SendTo("/chat/messages")
    public Message getMessages(Message message) {
        return message;
    }


}
