package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.entity.question.Tag;

import com.javamentor.qa.platform.models.dto.TagListDto;
import com.javamentor.qa.platform.models.dto.TagRecentDto;

import java.util.List;

public interface TagDtoDao {
    List<TagDto> getTagDtoPagination(int page, int size);

    List<TagListDto> getTagDtoPaginationOrderByAlphabet(int page, int size);
    List<TagListDto> getTagListDtoPaginationOrderByNewTag(int page, int size);

    List<TagListDto> getTagListDtoByPopularPagination(int page, int size);

    List<TagRecentDto> getTagRecentDtoPagination(int page, int size);

    public int getTotalResultCountTagDto();

    List<TagListDto> getTagListDtoPagination(int page, int size, String tagName);

    int getTotalCountTag(String tagName);
}
