package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.TagRecentDto;

import java.util.List;

public interface TagDtoDao {
    List<TagRecentDto> getTagRecentDtoPagination(int page, int size);

    int getTotalResultCountTagDto();
}
