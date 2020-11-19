package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.dto.TagListDto;
import com.javamentor.qa.platform.models.dto.TagRecentDto;
import com.javamentor.qa.platform.models.entity.question.Tag;

import java.util.Optional;


public interface TagDtoService {
    PageDto<TagDto, Object> getTagDtoPaginationByPopular(int page, int size);

    PageDto<TagListDto, Object> getTagDtoPaginationOrderByAlphabet(int page, int size);

    PageDto<TagListDto, Object> getTagListDtoByPopularPagination(int page, int size);

    PageDto<TagListDto, Object> getTagDtoPaginationWithSearch(int page, int size, String tagName);

    PageDto<TagRecentDto, Object> getTagRecentDtoPagination(int page, int size);

    PageDto<TagRecentDto, Object> getTagRecentDtoChildTagById(int page, int size, Long tagId);

}
