package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.dto.TagListDto;

import java.util.List;

public interface TagDtoDao {
    List<TagDto> getTagDtoPagination(int page, int size);
    List<TagListDto> getTagDtoPaginationOrderByAlphabet(int page, int size);
    List<TagListDto> getTagListDtoPaginationOrderByNewTag(int page, int size);

     int getTotalResultCountTagDto();
}
