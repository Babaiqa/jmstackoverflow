package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.dto.TagRecentDto;

public interface TagDtoService {

    PageDto<TagDto, Object> getTagDtoPaginationByPopular(int page, int size);

    PageDto<TagRecentDto, Object> getTagRecentDtoPagination(int page, int size);
}
