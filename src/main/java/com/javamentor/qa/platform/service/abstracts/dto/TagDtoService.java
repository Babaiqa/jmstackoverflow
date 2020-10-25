package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.TagRecentDto;

public interface TagDtoService {

    PageDto<TagRecentDto, Object> getTagRecentDtoPagination(int page, int size);
}
