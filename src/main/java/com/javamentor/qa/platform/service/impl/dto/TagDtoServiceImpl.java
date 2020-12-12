package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.models.dto.*;
import com.javamentor.qa.platform.service.abstracts.dto.TagDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TagDtoServiceImpl implements TagDtoService {

    private final TagDtoDao tagDtoDao;

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
    public PageDto<TagListDto, Object> getTagDtoPaginationOrderByAlphabet(int page, int size) {

        PageDto<TagListDto, Object> pagelistDto = new PageDto<>();

        int totalResultCount = tagDtoDao.getTotalResultCountTagDto();

        pagelistDto.setItems(tagDtoDao.getTagDtoPaginationOrderByAlphabet(page, size));
        pagelistDto.setTotalResultCount(totalResultCount);
        pagelistDto.setCurrentPageNumber(page);
        pagelistDto.setItemsOnPage(size);
        pagelistDto.setTotalPageCount((int) Math.ceil(totalResultCount / (double) size));

        return pagelistDto;
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

    @Override
    public PageDto<TagRecentDto, Object> getTagRecentDtoChildTagById(int page, int size, Long tagId) {
        PageDto<TagRecentDto, Object> pageDto = new PageDto<>();

        int totalResultCount = tagDtoDao.getTotalResultChildTag(tagId);

        pageDto.setItems(tagDtoDao.getTagRecentDtoChildTagById(page, size, tagId));
        pageDto.setTotalResultCount(totalResultCount);
        pageDto.setCurrentPageNumber(page);
        pageDto.setItemsOnPage(size);
        pageDto.setTotalPageCount((int) Math.ceil(totalResultCount / (double) size));

        return pageDto;
    }


    @Override
    public PageDto<TagListDto, Object> getTagDtoPaginationWithSearch(int page, int size, String tagName) {

        PageDto<TagListDto, Object> pageDto = new PageDto<>();

        int totalResultCount = tagDtoDao.getTotalCountTag(tagName);

        pageDto.setItems(tagDtoDao.getTagListDtoPagination(page, size, tagName));
        pageDto.setTotalResultCount(totalResultCount);
        pageDto.setCurrentPageNumber(page);
        pageDto.setItemsOnPage(size);
        pageDto.setTotalPageCount((int) Math.ceil(totalResultCount / (double) size));

        return pageDto;
    }

    @Override
    public PageDto<TagListDto, Object> getTagListDtoPaginationOrderByNewTag(int page, int size) {
        PageDto<TagListDto, Object> pageDto = new PageDto<>();
        int totalResultCount = tagDtoDao.getTotalResultCountTagDto();

        pageDto.setItems(tagDtoDao.getTagListDtoPaginationOrderByNewTag(page, size));
        pageDto.setTotalResultCount(totalResultCount);
        pageDto.setCurrentPageNumber(page);
        pageDto.setItemsOnPage(size);
        pageDto.setTotalPageCount((int) Math.ceil(totalResultCount/(double)size));

        return pageDto;
    }

    @Override
    public List<IgnoredTagDto> getIgnoredTagsByPrincipal(long id) {
        return tagDtoDao.getIgnoredTagsByPrincipal(id);
    }

    @Override
    public List<TrackedTagDto> getTrackedTagsByPrincipal(long id) {
        return tagDtoDao.getTrackedTagsByPrincipal(id);
    }

}
