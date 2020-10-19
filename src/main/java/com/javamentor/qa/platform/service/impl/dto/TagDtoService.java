package com.javamentor.qa.platform.service.impl.dto;


import com.javamentor.qa.platform.dao.impl.dto.TagDtoDAO;
import com.javamentor.qa.platform.models.dto.TagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class TagDtoService {

    private final TagDtoDAO tagDtoDAO;

    @Autowired
    public TagDtoService(TagDtoDAO tagDtoDAO) {
        this.tagDtoDAO = tagDtoDAO;
    }

    public List<TagDto> getTagDtoPagination(int page, int size) {
        return tagDtoDAO.getTagDtoPagination(page,size);
    }
}
