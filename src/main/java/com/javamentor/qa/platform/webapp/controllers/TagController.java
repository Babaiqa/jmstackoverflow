package com.javamentor.qa.platform.webapp.controllers;

import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.service.impl.dto.TagDtoService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class TagController {

    private final TagDtoService tagDtoService;


    @GetMapping("popular}")
    @ApiOperation(value = "get page TagDto by popular", response = String.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returns the pagination List<TagDto> by popular", response = List.class),
            @ApiResponse(code = 400, message = "Page not found", response = String.class)
    })
    public ResponseEntity<?> getTagDtoPaginationByPopular(
            @ApiParam(name="page",value="number Page. type int", required = true, example="0")
            @RequestParam("page") int page,
            @ApiParam(name="size",value="Number of entries per page.type int", required = true, example="0")
                                                          @RequestParam("size") int size){

        if(page<=0||size<=0){
            return ResponseEntity.badRequest().body("Номер страницы и размер должны быть положительными");
        }
        List<TagDto> resultPage =tagDtoService.getTagDtoPagination(page, size);

        return !resultPage.isEmpty()?ResponseEntity.ok(resultPage):ResponseEntity.badRequest().body("Page not found");
    }

}
