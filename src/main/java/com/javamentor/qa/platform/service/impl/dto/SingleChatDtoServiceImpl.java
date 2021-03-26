package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.SingleChatDtoDao;
import com.javamentor.qa.platform.models.dto.SingleChatDto;
import com.javamentor.qa.platform.service.abstracts.dto.SingleChatDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SingleChatDtoServiceImpl implements SingleChatDtoService {

    private SingleChatDtoDao singleChatDtoDao;

    @Autowired
    public void setSingleChatDtoDao(SingleChatDtoDao singleChatDtoDao) {
        this.singleChatDtoDao = singleChatDtoDao;
    }

    @Override
    public Optional<SingleChatDto> findSingleChatDtoById(Long id) {
        return singleChatDtoDao.findSingleChatDtoById(id);
    }

    @Override
    public List<SingleChatDto> getAllSingleChatDto() {
        return singleChatDtoDao.getAllSingleChatDto();
    }
}
