package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.TagListDto;

public interface TagListDtoService {
    PageDto<TagListDto, Object> getTagListDtoPaginationOrderByNewTag(int page, int size);
}
