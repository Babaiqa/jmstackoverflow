package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.ChatDto;

import java.util.List;

public interface ChatDtoService {
    List<ChatDto> getAllChatsByUser(Long userId);
}
