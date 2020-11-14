package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.dto.TagListDto;
import com.javamentor.qa.platform.models.dto.TagRecentDto;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.service.abstracts.dto.TagDtoService;
import com.javamentor.qa.platform.service.abstracts.model.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagDtoServiceImpl implements TagDtoService {

    private final TagDtoDao tagDtoDao;
    private final TagService tagService;

    @Autowired
    public TagDtoServiceImpl(TagDtoDao tagDtoDao, TagService tagService) {
        this.tagDtoDao = tagDtoDao;
        this.tagService = tagService;
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
        // проверяем наличие тега и получаем его имя
        String tagName = Optional.ofNullable(tagService.getById(tagId).get().getName()).orElse("No tag name");

        // получаем список дочерних тегов типа TagRecentDto от тега с запрошенным id
        List<TagRecentDto> res = tagDtoDao.getTagRecentDtoChildTagById(page, size, tagId);
        // считаем количество вхождений дочернего тега в таблицу related_tag и число страниц результатов
        int totalResultCount = res.size();
        int totalPageCount = (int) Math.ceil(totalResultCount / (double) size);

        // присваиваем полю List<T> items объекта pageDto полученный список
        pageDto.setItems(res);
        pageDto.setTotalResultCount(totalResultCount);

        pageDto.setCurrentPageNumber(page);
        pageDto.setItemsOnPage(size);
        pageDto.setTotalPageCount(totalPageCount);

        return pageDto;
    }

    @Override
    public Optional<Tag> getTagById(Long tagId) {
        return tagService.getById(tagId);
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

}
