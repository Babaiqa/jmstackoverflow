package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.dto.TagRecentDto;
import com.javamentor.qa.platform.service.abstracts.dto.TagDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagDtoServiceImpl implements TagDtoService {

    private TagDtoDao tagDtoDao;

    @Autowired
    public TagDtoServiceImpl(TagDtoDao tagDtoDao) {
        this.tagDtoDao = tagDtoDao;
    }

    @Override
    public PageDto<TagDto, Object> getTagDtoPaginationByPopular(int page, int size) {

        PageDto<TagDto, Object> pageDto = new PageDto<>();

        int totalResultCount = tagDtoDao.getTotalResultCountTagDto();

        pageDto.setItems(tagDtoDao.getTagDtoPagination(page, size));
        pageDto.setTotalResultCount(totalResultCount);
        pageDto.setCurrentPageNumber(page);
        pageDto.setItemsOnPage(size);
        pageDto.setTotalPageCount((int) Math.ceil(totalResultCount / (double) size));

        return pageDto;
    }


    @Override
    public PageDto<TagRecentDto, Object> getTagRecentDtoPagination(int page, int size) {

        PageDto<TagRecentDto, Object> pageDto = new PageDto<>();

        int totalResultCount = tagDtoDao.getTotalResultCountTagDto();

        pageDto.setItems(tagDtoDao.getTagRecentDtoPagination(page, size));
        pageDto.setTotalResultCount(totalResultCount);
        pageDto.setCurrentPageNumber(page);
        pageDto.setItemsOnPage(size);
        pageDto.setTotalPageCount((int) Math.ceil(totalResultCount / (double) size));

        return pageDto;
    }
}
