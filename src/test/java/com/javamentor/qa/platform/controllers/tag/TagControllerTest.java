package com.javamentor.qa.platform.controllers.tag;

import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.AbstractIntegrationTest;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.entity.question.Tag;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@DataSet(value = {
        "dataset/question/tagQuestionApi.yml"}
        , cleanBefore = true, cleanAfter = true)
public class TagControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String POPULAR = "/api/tag/popular";
    private static final String BAD_REQUEST_MESSAGE = "Номер страницы и размер должны быть положительными. Максимальное количество записей на странице 100";


    @Test
    public void requestGetTagDtoPaginationByPopular() throws Exception {
        PageDto<TagDto, Object> expected = new PageDto<>();
        expected.setCurrentPageNumber(1);
        expected.setTotalPageCount(1);
        expected.setTotalResultCount(5);
        expected.setItemsOnPage(10);

        List<TagDto> expectedItems = new ArrayList<>();
        expectedItems.add(new TagDto(1L, "java"));
        expectedItems.add(new TagDto(2L, "java1"));
        expectedItems.add(new TagDto(3L, "java2"));
        expectedItems.add(new TagDto(4L, "java3"));
        expectedItems.add(new TagDto(5L, "java4"));
        expected.setItems(expectedItems);

        String resultContext = mockMvc.perform(get(POPULAR)
                .param("page", "1")
                .param("size", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.currentPageNumber").isNotEmpty())
                .andExpect(jsonPath("$.totalPageCount").isNotEmpty())
                .andExpect(jsonPath("$.totalResultCount").isNotEmpty())
                .andExpect(jsonPath("$.items").isNotEmpty())
                .andExpect(jsonPath("$.itemsOnPage").isNotEmpty())
                .andReturn().getResponse().getContentAsString();

        PageDto<TagDto, Object> actual = objectMapper.readValue(resultContext, PageDto.class);
        Assert.assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void requestNegativePageGetTagDtoPaginationByPopular() throws Exception {
        mockMvc.perform(get(POPULAR)
                .param("page", "-1")
                .param("size", "10"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8"))
                .andExpect(content().string(BAD_REQUEST_MESSAGE));
    }

    @Test
    public void requestNegativeSizeGetTagDtoPaginationByPopular() throws Exception {
        mockMvc.perform(get(POPULAR)
                .param("page", "1")
                .param("size", "0"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8"))
                .andExpect(content().string(BAD_REQUEST_MESSAGE));
    }

    @Test
    public void requestIncorrectSizeGetTagDtoPaginationByPopular() throws Exception {
        mockMvc.perform(get(POPULAR)
                .param("page", "1")
                .param("size", "101"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8"))
                .andExpect(content().string(BAD_REQUEST_MESSAGE));
    }

    @Test
    public void requestPageDontExistsGetTagDtoPaginationByPopular() throws Exception {
        mockMvc.perform(get(POPULAR)
                .param("page", "13")
                .param("size", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.currentPageNumber").isNotEmpty())
                .andExpect(jsonPath("$.totalPageCount").isNotEmpty())
                .andExpect(jsonPath("$.totalResultCount").isNotEmpty())
                .andExpect(jsonPath("$.items").isEmpty());
    }

}
