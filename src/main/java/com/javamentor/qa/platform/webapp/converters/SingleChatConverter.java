package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.SingleChatDto;
import com.javamentor.qa.platform.models.entity.chat.SingleChat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class SingleChatConverter {

    @Mapping(source = "singleChat.id", target = "id")
    @Mapping(source = "singleChat.chat.id", target = "chatId")
    @Mapping(source = "singleChat.chat.title", target = "title")
    @Mapping(source = "singleChat.userOne.id", target = "userOneId")
    @Mapping(source = "singleChat.useTwo.id", target = "userTwoId")
    public abstract SingleChatDto singleChatToSingleChatDto(SingleChat singleChat);
}
