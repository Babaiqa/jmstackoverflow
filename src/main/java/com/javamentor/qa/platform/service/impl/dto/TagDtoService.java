package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.models.dto.TagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagDtoService {

    private final TagDtoDao tagDtoDAO;

    @Autowired
    public TagDtoService(TagDtoDao tagDtoDao) {
        this.tagDtoDAO = tagDtoDao;
    }

    public List<TagDto> getTagDtoPagination(int page, int size) {
        return tagDtoDAO.getTagDtoPagination(page, size);
    }
}
