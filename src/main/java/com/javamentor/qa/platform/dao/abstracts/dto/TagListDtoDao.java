package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.TagListDto;

import java.util.List;

public interface TagListDtoDao {
    List<TagListDto> getTagListDtoPaginationOrderByNewTag(int page, int size);
    int getTotalResultCountTagListDto();
}
