package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.BookmarkDto;

import java.util.Optional;

public interface BookmarkDtoService {
    Optional<BookmarkDto> getBookmarkDtoByUserId(Long id);
}
