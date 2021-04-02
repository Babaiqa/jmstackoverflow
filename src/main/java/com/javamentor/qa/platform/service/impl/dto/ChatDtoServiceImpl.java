package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.ChatDtoDao;
import com.javamentor.qa.platform.models.dto.ChatDto;
import com.javamentor.qa.platform.service.abstracts.dto.ChatDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChatDtoServiceImpl implements ChatDtoService {

    private final ChatDtoDao chatDtoDao;

    @Autowired
    public ChatDtoServiceImpl(ChatDtoDao chatDtoDao) {
        this.chatDtoDao = chatDtoDao;
    }

    @Override
    @Transactional
    public List<ChatDto> getAllChatsByUser(Long userId) {
        return chatDtoDao.getAllChatsByUser(userId);
    }
}
