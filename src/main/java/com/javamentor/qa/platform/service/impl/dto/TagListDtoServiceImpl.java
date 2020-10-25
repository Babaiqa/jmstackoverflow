package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagListDtoDao;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.TagListDto;
import com.javamentor.qa.platform.service.abstracts.dto.TagListDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagListDtoServiceImpl implements TagListDtoService {

    private TagListDtoDao tagListDtoDao;

    @Autowired
    public TagListDtoServiceImpl(TagListDtoDao tagListDtoDao) {
        this.tagListDtoDao = tagListDtoDao;
    }

    @Override
    public PageDto<TagListDto, Object> getTagListDtoPaginationOrderByNewTag(int page, int size) {
        PageDto<TagListDto, Object> pageDto = new PageDto<>();
        int totalResultCount = tagListDtoDao.getTotalResultCountTagListDto();

        pageDto.setItems(tagListDtoDao.getTagListDtoPaginationOrderByNewTag(page, size));
        pageDto.setTotalResultCount(totalResultCount);
        pageDto.setCurrentPageNumber(page);
        pageDto.setItemsOnPage(size);
        pageDto.setTotalPageCount(totalResultCount/size);

        return pageDto;
    }
}
