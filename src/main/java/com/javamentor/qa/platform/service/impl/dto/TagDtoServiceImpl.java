package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.dto.TagListDto;
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
    public PageDto<TagListDto, Object> getTagListDtoByPopularPagination(int page, int size) {

        PageDto<TagListDto, Object> pageDto = new PageDto<>();

        int totalResultCount = tagDtoDao.getTotalResultCountTagDto();

        pageDto.setItems(tagDtoDao.getTagListDtoByPopularPagination(page, size));
        pageDto.setTotalResultCount(totalResultCount);
        pageDto.setCurrentPageNumber(page);
        pageDto.setItemsOnPage(size);
        pageDto.setTotalPageCount((int) Math.ceil(totalResultCount / (double) size));

        return pageDto;
    }

    @Override
    public PageDto<TagListDto,Object> getTagDtoPaginationOrderByAlphabet(int page, int size) {

        PageDto<TagListDto,Object> pagelistDto = new PageDto<>();

        int totalResultCount=tagDtoDao.getTotalResultCountTagDto();

        pagelistDto.setItems(tagDtoDao.getTagDtoPaginationOrderByAlphabet(page, size));
        pagelistDto.setTotalResultCount(totalResultCount);
        pagelistDto.setCurrentPageNumber(page);
        pagelistDto.setItemsOnPage(size);
        pagelistDto.setTotalPageCount((int) Math.ceil(totalResultCount/(double)size));

        return pagelistDto;
    }

}
