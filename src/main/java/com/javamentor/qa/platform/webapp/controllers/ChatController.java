package com.javamentor.qa.platform.webapp.controllers;

import com.javamentor.qa.platform.dao.abstracts.model.SingleChatDao;
import com.javamentor.qa.platform.dao.impl.model.SingleChatDaoImpl;
import com.javamentor.qa.platform.models.dto.*;
import com.javamentor.qa.platform.models.entity.chat.Chat;
import com.javamentor.qa.platform.models.entity.chat.SingleChat;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.util.OnCreate;
import com.javamentor.qa.platform.service.abstracts.dto.MessageDtoService;
import com.javamentor.qa.platform.models.entity.chat.Message;
import com.javamentor.qa.platform.security.util.SecurityHelper;
import com.javamentor.qa.platform.service.abstracts.dto.ChatDtoService;
import com.javamentor.qa.platform.service.abstracts.dto.SingleChatDtoService;
import com.javamentor.qa.platform.service.abstracts.model.ChatService;
import com.javamentor.qa.platform.service.abstracts.model.MessageService;
import com.javamentor.qa.platform.service.abstracts.model.SingleChatService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.webapp.converters.SingleChatConverter;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("/api/chat")
@Api(value = "ChatApi")
public class ChatController {

    private final SecurityHelper securityHelper;
    private final ChatDtoService chatDtoService;
    private final ChatService chatService;
    private final SingleChatDtoService singleChatDtoService;
    private final MessageDtoService messageDtoService;
    private final MessageService messageService;
    private final SingleChatConverter singleChatConverter;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final UserService userService;
    private final SingleChatService singleChatService;

    private static final int MAX_ITEMS_ON_PAGE = 100;

    @Autowired
    public ChatController(SingleChatDtoService singleChatDtoService, SecurityHelper securityHelper, ChatDtoService chatDtoService, ChatService chatService, MessageDtoService messageDtoService, MessageService messageService, SingleChatConverter singleChatConverter, SimpMessagingTemplate simpMessagingTemplate, UserService userService, SingleChatService singleChatService) {
        this.singleChatDtoService = singleChatDtoService;
        this.securityHelper = securityHelper;
        this.chatDtoService = chatDtoService;
        this.chatService = chatService;
        this.messageDtoService = messageDtoService;
        this.messageService = messageService;
        this.singleChatConverter = singleChatConverter;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.userService = userService;
        this.singleChatService = singleChatService;
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
        List<Object> id = new ArrayList<>();
        id.add(securityHelper.getPrincipal().getId());
        allMessage.setMeta(id);
        return ResponseEntity.ok(allMessage);
    }


    @MessageMapping("/message/{chatId}")
    public ResponseEntity proceedMessage(@DestinationVariable String chatId, Map<String, String> message) throws Exception {
        String messageText = message.get("message");
        String currentUserId = message.get("userSender");



        Optional<Chat> chatOptional = chatService.getById(Long.parseLong(chatId));
        Optional<User> userOptional = userService.getById(Long.parseLong(currentUserId));

        if (!(chatOptional.isPresent() && userOptional.isPresent())) {
            return ResponseEntity.badRequest().body("В запросе отсутствуют данные: userSender или chatId");
        }
        Chat chat = chatOptional.get();
        User userSender = userOptional.get();

        Message messageEntity = new Message(messageText, userSender, chat);
        messageService.persist(messageEntity);

        simpMessagingTemplate.convertAndSend("/chat/" + chatId + "/message", message);
        return ResponseEntity.ok(message);
    }

    @GetMapping(path = "/byuser")
    @ApiOperation(value = "Get Chats By User. MAX ITEMS ON PAGE=" + MAX_ITEMS_ON_PAGE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returns the pagination List<ChatDto>"),
    })
    public ResponseEntity<?> getAllChatsByUserPagination(
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

        Long userId = securityHelper.getPrincipal().getId();

        PageDto<ChatDto, Object> allChatsByUser = chatDtoService.getAllChatsByUserPagination(userId, page, size);

        return ResponseEntity.ok(allChatsByUser);

    }

    @PostMapping("/single/add")
    @Validated(OnCreate.class)
    @ResponseBody
    @ApiOperation(value = "add single chat", response = String.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "adds single chat", response = SingleChat.class),
            @ApiResponse(code = 400, message = "single chat was not added", response = String.class)
    })
    public ResponseEntity<?> addSingleChat(@Valid @RequestBody CreateSingleChatDto createSingleChatDto){
        User user = securityHelper.getPrincipal();

        if(!userService.existsById(createSingleChatDto.getUserTwoId())){
            ResponseEntity.badRequest().body("userTwoId doesn't exist");
        }
        SingleChat singleChat = new SingleChat();
        singleChat.getChat().setTitle(createSingleChatDto.getTitle());
        singleChat.setUserOne(userService.getById(user.getId()).get());
        singleChat.setUseTwo(userService.getById(createSingleChatDto.getUserTwoId()).get());
        singleChatService.persist(singleChat);
        return ResponseEntity.ok(singleChatConverter.singleChatToSingleChatDto(singleChat));
    }
}
