package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.service.abstracts.dto.TagDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagDtoServiceImpl implements TagDtoService {

    private final TagDtoDao tagDtoDAO;

    @Autowired
    public TagDtoServiceImpl(TagDtoDao tagDtoDao) {
        this.tagDtoDAO = tagDtoDao;
    }
   @Override
    public List<TagDto> getTagDtoPagination(int page, int size) {
       PageDto<TagDto,?> pageDto = new PageDto<>();

       pageDto.setItems(tagDtoDAO.getTagDtoPagination(page, size));
       int totalResultCount=tagDtoDAO.getTotalResultCountTagDto();
       pageDto.setTotalResultCount(totalResultCount);
       pageDto.setCurrentPageNumber(page);
       pageDto.setTotalPageCount((int) Math.ceil(totalResultCount/size));

        return tagDtoDAO.getTagDtoPagination(page, size);
    }

}
