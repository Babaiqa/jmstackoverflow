package com.javamentor.qa.platform.service.impl.dto;


import com.javamentor.qa.platform.dao.impl.dto.TagDtoDAO;
import com.javamentor.qa.platform.models.dto.TagDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagDtoService {

    private final TagDtoDAO tagDtoDAO;


    public List<TagDto> getTagDtoPagination(int page, int size) {
        return tagDtoDAO.getTagDtoPagination(page,size);
    }
}
