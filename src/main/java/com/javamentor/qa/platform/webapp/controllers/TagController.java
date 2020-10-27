package com.javamentor.qa.platform.webapp.controllers;

import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.dto.TagListDto;
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

    @Autowired
    public TagController(TagDtoService tagDtoService) {

        this.tagDtoService = tagDtoService;
    }

    @GetMapping("popular")
    @ApiOperation(value = "get page TagDto by popular. MAX SIZE ENTRIES ON PAGE=100", response = String.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returns the pagination List<TagDto> by popular", response = List.class),
    })
    public ResponseEntity<?> getTagDtoPaginationByPopular(
            @ApiParam(name = "page", value = "Number Page. type int", required = true, example = "0")
            @RequestParam("page") int page,
            @ApiParam(name = "size", value = "Number of entries per page.Type int." +
                    " Максимальное количество записей на странице"+ MAX_ITEMS_ON_PAGE ,
                     example = "10")
            @RequestParam("size") int size) {

        if (page <= 0 || size <= 0 || size > MAX_ITEMS_ON_PAGE) {
            return ResponseEntity.badRequest().body("Номер страницы и размер должны быть " +
                    "положительными. Максимальное количество записей на странице " + MAX_ITEMS_ON_PAGE);
        }
        PageDto<TagDto,Object> resultPage = tagDtoService.getTagDtoPaginationByPopular(page, size);

        return  ResponseEntity.ok(resultPage);

    }

    @GetMapping("new/order")
    @ApiOperation(value = "Get page TagListDto by new tags. MAX SIZE ENTRIES ON PAGE = 100", response = String.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Return the pagination List<TagListDto> ordered by new tags", response = List.class)
    })
    public ResponseEntity<?> getTagListDtoPaginationOrderByNewTag(
            @ApiParam(name = "page", value = "Number page. Type int.", required = true, example = "1")
            @RequestParam("page") int page,
            @ApiParam(name = "size", value = "Number of entries per page. Type int." +
                    "Recommended number of items per page "+ MAX_ITEMS_ON_PAGE, example = "10")
            @RequestParam("size") int size)
    {
        if (page <= 0 || size <= 0 || size > MAX_ITEMS_ON_PAGE) {
            return ResponseEntity.badRequest().body("Page and Size have to be positive. " +
                    "Max number of items per page " + MAX_ITEMS_ON_PAGE);
        }

        PageDto<TagListDto, Object> pageDto = tagDtoService.getTagListDtoPaginationOrderByNewTag(page, size);

        if (pageDto.getItems().isEmpty()) {
            return ResponseEntity.status(404).body("Not Found");
        }

        return ResponseEntity.ok(pageDto);
    }

}
