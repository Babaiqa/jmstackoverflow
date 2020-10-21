package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.TagDto;
import java.util.List;

public interface TagDtoDao {
    List<TagDto> getTagDtoPagination(int page, int size);
    List<TagDto> getTagDtoPaginationOrderByAlphabet(int page, int size);

    public int getTotalResultCountTagDto();
}
