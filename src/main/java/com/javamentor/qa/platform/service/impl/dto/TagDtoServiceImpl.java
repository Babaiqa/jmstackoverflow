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

    private final TagDtoDao tagDtoDAO;

    @Autowired
    public TagDtoServiceImpl(TagDtoDao tagDtoDao) {
        this.tagDtoDAO = tagDtoDao;
    }


   @Override
    public PageDto<TagDto,Object> getTagDtoPaginationByPopular(int page, int size) {

       PageDto<TagDto,Object> pageDto = new PageDto<>();

       int totalResultCount=tagDtoDAO.getTotalResultCountTagDto();

       pageDto.setItems(tagDtoDAO.getTagDtoPagination(page, size));
       pageDto.setTotalResultCount(totalResultCount);
       pageDto.setCurrentPageNumber(page);
       pageDto.setItemsOnPage(size);
       pageDto.setTotalPageCount((int) Math.ceil(totalResultCount/(double)size));

        return pageDto;
    }

    @Override
    public PageDto<TagListDto,Object> getTagDtoPaginationOrderByAlphabet(int page, int size) {

        PageDto<TagListDto,Object> pagelistDto = new PageDto<>();

        int totalResultCount=tagDtoDAO.getTotalResultCountTagDto();

        pagelistDto.setItems(tagDtoDAO.getTagDtoPaginationOrderByAlphabet(page, size));
        pagelistDto.setTotalResultCount(totalResultCount);
        pagelistDto.setCurrentPageNumber(page);
        pagelistDto.setItemsOnPage(size);
        pagelistDto.setTotalPageCount((int) Math.ceil(totalResultCount/(double)size));

        return pagelistDto;
    }

    public PageDto<TagListDto, Object> getTagListDtoPaginationOrderByNewTag(int page, int size) {
        PageDto<TagListDto, Object> pageDto = new PageDto<>();
        int totalResultCount = tagDtoDAO.getTotalResultCountTagDto();

        pageDto.setItems(tagDtoDAO.getTagListDtoPaginationOrderByNewTag(page, size));
        pageDto.setTotalResultCount(totalResultCount);
        pageDto.setCurrentPageNumber(page);
        pageDto.setItemsOnPage(size);
        pageDto.setTotalPageCount((int) Math.ceil(totalResultCount/(double)size));

        return pageDto;
    }


}
