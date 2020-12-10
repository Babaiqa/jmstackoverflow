package com.javamentor.qa.platform.webapp.controllers;

import com.javamentor.qa.platform.models.dto.*;
import com.javamentor.qa.platform.service.abstracts.dto.TagDtoService;
import com.javamentor.qa.platform.service.abstracts.dto.UserDtoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/tag/")
@Api(value = "TagApi")
public class TagController {

    private final TagDtoService tagDtoService;
    private final UserDtoService userDtoService;

    private static final int MAX_ITEMS_ON_PAGE = 100;

    @Autowired
    public TagController(TagDtoService tagDtoService, UserDtoService userDtoService) {
        this.tagDtoService = tagDtoService;
        this.userDtoService = userDtoService;
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
                    " Максимальное количество записей на странице" + MAX_ITEMS_ON_PAGE,
                    example = "10")
            @RequestParam("size") int size) {

        if (page <= 0 || size <= 0 || size > MAX_ITEMS_ON_PAGE) {
            return ResponseEntity.badRequest().body("Номер страницы и размер должны быть " +
                    "положительными. Максимальное количество записей на странице " + MAX_ITEMS_ON_PAGE);
        }
        PageDto<TagDto, Object> resultPage = tagDtoService.getTagDtoPaginationByPopular(page, size);

        return ResponseEntity.ok(resultPage);

    }


    @GetMapping("alphabet/order")
    @ApiOperation(value = "get page TagDto by alphabet. MAX SIZE ENTRIES ON PAGE=100", response = String.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returns the pagination List<TagDto> by alphabet", response = List.class),
    })
    public ResponseEntity<?> getTagDtoPaginationOrderByAlphabet(
            @ApiParam(name = "page", value = "Number Page. type int", required = true, example = "0")
            @RequestParam("page") int page,
            @ApiParam(name = "size", value = "Number of entries per page.Type int." +
                    " Максимальное количество записей на странице" + MAX_ITEMS_ON_PAGE,
                    example = "10")
            @RequestParam("size") int size) {

        if (page <= 0 || size <= 0 || size > MAX_ITEMS_ON_PAGE) {
            return ResponseEntity.badRequest().body("Номер страницы и размер должны быть " +
                    "положительными. Максимальное количество записей на странице " + MAX_ITEMS_ON_PAGE);
        }
        PageDto<TagListDto, Object> resultPage = tagDtoService.getTagDtoPaginationOrderByAlphabet(page, size);

        return ResponseEntity.ok(resultPage);

    }

    @GetMapping("order/popular")
    @ApiOperation(value = "get page TagListDto by popular. MAX SIZE ENTRIES ON PAGE=100")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returns the pagination List<TagListDto> by popular"),
    })
    public ResponseEntity<?> getTagListDtoByPopularPagination(
            @ApiParam(name = "page", value = "Number Page. type int", required = true, example = "0")
            @RequestParam("page") int page,
            @ApiParam(name = "size", value = "Number of entries per page.Type int." +
                    " Максимальное количество записей на странице" + MAX_ITEMS_ON_PAGE,
                    example = "10")
            @RequestParam("size") int size) {

        if (page <= 0 || size <= 0 || size > MAX_ITEMS_ON_PAGE) {
            return ResponseEntity.badRequest().body("Номер страницы и размер должны быть " +
                    "положительными. Максимальное количество записей на странице " + MAX_ITEMS_ON_PAGE);
        }
        PageDto<TagListDto, Object> resultPage = tagDtoService.getTagListDtoByPopularPagination(page, size);

        return ResponseEntity.ok(resultPage);

    }

    @GetMapping("new/order")
    @ApiOperation(value = "Get page TagListDto by new tags. MAX SIZE ENTRIES ON PAGE = 100", response = String.class)
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

        return ResponseEntity.ok(pageDto);

    }


    @GetMapping("recent")
    @ApiOperation(value = "get page TagRecentDto. MAX SIZE ENTRIES ON PAGE=100", response = String.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returns the pagination List<TagRecentDto>", response = List.class),
    })
    public ResponseEntity<?> getTagRecentDtoPagination(
            @ApiParam(name = "page", value = "Number Page. type int", required = true, example = "0")
            @RequestParam("page") int page,
            @ApiParam(name = "size", value = "Number of entries per page.Type int." +
                    " Максимальное количество записей на странице" + MAX_ITEMS_ON_PAGE,
                    example = "10")
            @RequestParam("size") int size) {

        if (page <= 0 || size <= 0 || size > MAX_ITEMS_ON_PAGE) {
            return ResponseEntity.badRequest().body("Номер страницы и размер должны быть " +
                    "положительными. Максимальное количество записей на странице " + MAX_ITEMS_ON_PAGE);
        }
        PageDto<TagRecentDto, Object> resultPage = tagDtoService.getTagRecentDtoPagination(page, size);

        return ResponseEntity.ok(resultPage);

    }

    @GetMapping(value = "name", params = {"page", "size", "name"})
    @ApiOperation(value = "get page TagListDto with search by tag name. MAX SIZE ENTRIES ON PAGE=100", response = PageDto.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returns the pagination PageDto<TagListDto> with search by tag name", response = PageDto.class),
    })
    public ResponseEntity<?> getTagName(
            @ApiParam(name = "page", value = "Number Page. Type int",
                    example = "10")
            @RequestParam("page") int page,
            @ApiParam(name = "name", value = "Incomplete tag name",
                    example = "tag")
            @RequestParam("name") String tagName,
            @ApiParam(name = "size", value = "Number of entries per page.Type int." +
                    " Maximum number of records per page -"+ MAX_ITEMS_ON_PAGE ,
                    example = "10")
            @RequestParam("size") int size) {

        if (page <= 0 || size <= 0 || size > MAX_ITEMS_ON_PAGE) {
            return ResponseEntity.badRequest().body("Номер страницы и размер должны быть положительными. " +
                    "Максимальное количество записей на странице " + MAX_ITEMS_ON_PAGE);
        }

        return ResponseEntity.ok(tagDtoService.getTagDtoPaginationWithSearch(page, size, tagName));
    }

    @GetMapping(value = "{tagId}/child", params = {"page", "size"})
    @ApiOperation(value = "get page TagRecentDto with child tags by tag id. The results are sorted by popularity. MAX SIZE ENTRIES ON PAGE=100", response = PageDto.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returns the pagination PageDto<TagRecentDto> with child tags by tag id", response = PageDto.class),
            @ApiResponse(code = 400, message = "Wrong ID or page number", response = String.class)
    })
    public ResponseEntity<?> getChildTagsById(
            @ApiParam(name = "page", value = "Number Page. Type int",
                    example = "10")
            @RequestParam("page") int page,
            @PathVariable Long tagId,
            @ApiParam(name = "size", value = "Number of entries per page.Type int." +
                    " Maximum number of records per page -"+ MAX_ITEMS_ON_PAGE ,
                    example = "10")
            @RequestParam("size") int size){
        if (page <= 0 || size <= 0 || size > MAX_ITEMS_ON_PAGE) {
            return ResponseEntity.badRequest().body("Номер страницы и размер должны быть " +
                    "положительными. Максимальное количество записей на странице " + MAX_ITEMS_ON_PAGE);
        }

        PageDto<TagRecentDto, Object> resultPage = tagDtoService.getTagRecentDtoChildTagById(page, size, tagId);

        return ResponseEntity.ok(resultPage);
    }

    @GetMapping(value = "ignored")
    @ApiOperation(value = "get list to ignored tags", response = TagDto.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returns the List<IgnoredTagDto> list", response = TagDto.class)
    })
    public ResponseEntity<?> getUserIgnoredTags() {
        List<IgnoredTagDto> tags =
                tagDtoService.getIgnoredTagsByPrincipal(userDtoService.getPrincipal().get().getId());
        return ResponseEntity.ok(tags);
    }
}





