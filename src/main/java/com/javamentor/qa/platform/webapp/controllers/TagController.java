package com.javamentor.qa.platform.webapp.controllers;

import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.dto.TagListDto;
import com.javamentor.qa.platform.service.abstracts.dto.TagDtoService;
import com.javamentor.qa.platform.service.abstracts.dto.TagListDtoService;
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
    private final TagListDtoService tagListDtoService;

    private final int MAX_ITEMS_ON_PAGE = 100;

    @Autowired
    public TagController(TagDtoService tagDtoService, TagListDtoService tagListDtoService) {

        this.tagDtoService = tagDtoService;
        this.tagListDtoService = tagListDtoService;
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

    @GetMapping(value = "new/order", params = {"page", "size"})
    public ResponseEntity<PageDto<TagListDto, Object>> getTagListDtoPaginationOrderByNewTag(@RequestParam("page") int page,
                                                                  @RequestParam("size") int size) {
        PageDto<TagListDto, Object> pageDto = tagListDtoService.getTagListDtoPaginationOrderByNewTag(page, size);

        return ResponseEntity.ok(pageDto);
    }

}
