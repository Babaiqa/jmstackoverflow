package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.CreateSingleChatDto;
import com.javamentor.qa.platform.models.dto.SingleChatDto;
import com.javamentor.qa.platform.models.entity.chat.SingleChat;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class SingleChatConverter {

    @Autowired
    UserService userService;

    @Mapping(source = "singleChat.id", target = "id")
    @Mapping(source = "singleChat.chat.title", target = "title")
    @Mapping(source = "singleChat.userSender.id", target = "userSenderId")
    @Mapping(source = "singleChat.userRecipient.id", target = "userRecipientId")
    @Mapping(source = "singleChat.userSender.id", target = "userSenderIdCheck")
    public abstract SingleChatDto singleChatToSingleChatDto(SingleChat singleChat);

    @Mapping(target = "userSender", source = "userSenderId", qualifiedByName = "mapUser")
    @Mapping(target = "userRecipient", source = "userRecipientId", qualifiedByName = "mapUser")
    @Mapping(target = "chat.chatType", source = "chatType")
    @Mapping(source = "title", target = "chat.title")
    public abstract SingleChat createSingleChatDtoToSingleChat(CreateSingleChatDto createSingleChatDto);

    @Named("mapUser")
    public User mapUser(Long id){
        return userService.getById(id).get();
    }
}
