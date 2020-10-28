package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.dto.TagListDto;


public interface TagDtoService {
    PageDto<TagDto, Object> getTagDtoPaginationByPopular(int page, int size);

    PageDto<TagListDto, Object> getTagListDtoByPopularPagination(int page, int size);
}
