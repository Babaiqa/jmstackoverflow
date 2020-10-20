package com.javamentor.qa.platform.webapp.controllers;

import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.service.abstracts.dto.TagDtoService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/taq/")
@Api(value = "TaqApi")
public class TagController {

    private final TagDtoService tagDtoService;

    private final int MAX_ITEMS_ON_PAGE = 100;
    private final String DEFAULT_VALUE_ITEMS_ON_PAGE="12";

    @Autowired
    public TagController(TagDtoService tagDtoService) {
        this.tagDtoService = tagDtoService;
    }

    @GetMapping("popular")
    @ApiOperation(value = "get page TagDto by popular. MAX SIZE ENTRIES ON PAGE=100", response = String.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returns the pagination List<TagDto> by popular", response = List.class),
            @ApiResponse(code = 400, message = "Page not found", response = String.class)
    })
    public ResponseEntity<?> getTagDtoPaginationByPopular(
            @ApiParam(name = "page", value = "Number Page. type int", required = true, example = "0")
            @RequestParam("page") int page,
            @ApiParam(name = "size", value = "Number of entries per page.Type int. By default = "
                    + DEFAULT_VALUE_ITEMS_ON_PAGE+" Максимальное количество записей на странице"+ MAX_ITEMS_ON_PAGE ,
                     example = "10")
            @RequestParam(name="size", required = false, defaultValue = DEFAULT_VALUE_ITEMS_ON_PAGE) int size) {

        if (page <= 0 || size <= 0 || size > MAX_ITEMS_ON_PAGE) {
            return ResponseEntity.badRequest().body("Номер страницы и размер должны быть " +
                    "положительными. Максимальное количество записей на странице " + MAX_ITEMS_ON_PAGE);
        }
        PageDto<TagDto,?> resultPage = tagDtoService.getTagDtoPagination(page, size);

        return !resultPage.getItems().isEmpty() ? ResponseEntity.ok(resultPage)
                : ResponseEntity.badRequest().body("Page not found");
    }

}
