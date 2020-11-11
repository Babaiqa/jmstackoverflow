package com.javamentor.qa.platform.controllers.user;

import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.AbstractIntegrationTest;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.UserDtoList;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


public class UserControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @DataSet(value = {"dataset/question/roleQuestionApi.yml",
            "dataset/user/usersQuestionApi.yml",
            "dataset/question/questionQuestionApi.yml",
            "dataset/question/tagQuestionApi.yml",
            "dataset/question/question_has_tagQuestionApi.yml"}, cleanBefore = true, cleanAfter = true)
    @Test
    public void requestUserTagReputationOverMonth() throws Exception {

        PageDto<UserDtoList, Object> expected = new PageDto<>();
        expected.setCurrentPageNumber(1);
        expected.setTotalPageCount(1);
        expected.setTotalResultCount(5);
        expected.setItemsOnPage(10);

        List<UserDtoList> expectedItems = new ArrayList<>();
        expectedItems.add(new UserDtoList(1L, "Teat", "null", 15));
        expectedItems.add(new UserDtoList(2L, "Tot", null, 0));
        expectedItems.add(new UserDtoList(3L, "Tot", "tra ta ta", 2));
        expectedItems.add(new UserDtoList(4L, "Tot", null, 0));
        expectedItems.add(new UserDtoList(5L, "Tot", null, 0));
        expected.setItems(expectedItems);

        String resultContext =
                mockMvc.perform(get("/api/user/order/reputation/month")
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

        PageDto<UserDtoList, Object> actual = objectMapper.readValue(resultContext, PageDto.class);
        Assert.assertEquals(expected.getClass(), actual.getClass());
        Assert.assertEquals(expected.getCurrentPageNumber(), actual.getCurrentPageNumber());
        Assert.assertEquals(expected.getTotalPageCount(), actual.getTotalPageCount());
        Assert.assertEquals(expected.getTotalResultCount(), actual.getTotalResultCount());
        Assert.assertEquals(expected.getItemsOnPage(), actual.getItemsOnPage());
        Assert.assertEquals(expected.getItems().size(), actual.getItems().size());
    }

    @DataSet(value = {"dataset/question/roleQuestionApi.yml",
            "dataset/user/usersQuestionApi.yml",
            "dataset/question/questionQuestionApi.yml",
            "dataset/question/tagQuestionApi.yml",
            "dataset/question/question_has_tagQuestionApi.yml"}, cleanBefore = true, cleanAfter = true)
    @Test
    public void requestNegativePageUserTagReputationOverMonth() throws Exception {
        mockMvc.perform(get("/api/user/order/reputation/month")
                .param("page", "-1")
                .param("size", "10"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8"))
                .andExpect(content().string("Номер страницы и размер должны быть положительными. Максимальное количество записей на странице 100"));
    }

    @DataSet(value = {"dataset/question/roleQuestionApi.yml",
            "dataset/user/usersQuestionApi.yml",
            "dataset/question/questionQuestionApi.yml",
            "dataset/question/tagQuestionApi.yml",
            "dataset/question/question_has_tagQuestionApi.yml"}, cleanBefore = true, cleanAfter = true)
    @Test
    public  void requestNegativeSizeUserTagReputationOverMonth() throws Exception {
        mockMvc.perform(get("/api/user/order/reputation/month")
                .param("page", "1")
                .param("size", "0"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8"))
                .andExpect(content().string("Номер страницы и размер должны быть положительными. Максимальное количество записей на странице 100"));
    }

    @DataSet(value = {"dataset/question/roleQuestionApi.yml",
            "dataset/user/usersQuestionApi.yml",
            "dataset/question/questionQuestionApi.yml",
            "dataset/question/tagQuestionApi.yml",
            "dataset/question/question_has_tagQuestionApi.yml"}, cleanBefore = true, cleanAfter = true)
    @Test
    public void requestIncorrectSizeUserTagReputationOverMonth() throws Exception {
        mockMvc.perform(get("/api/user/order/reputation/month")
                .param("page", "1")
                .param("size", "101"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8"))
                .andExpect(content().string("Номер страницы и размер должны быть положительными. Максимальное количество записей на странице 100"));
    }

    @DataSet(value = {"dataset/question/roleQuestionApi.yml",
            "dataset/user/usersQuestionApi.yml",
            "dataset/question/questionQuestionApi.yml",
            "dataset/question/tagQuestionApi.yml",
            "dataset/question/question_has_tagQuestionApi.yml"}, cleanBefore = true, cleanAfter = true)
    @Test
    public void requestPageDontExistsUserTagReputationOverMonth() throws Exception {
        mockMvc.perform(get("/api/user/order/reputation/month")
                .param("page", "99")
                .param("size", "99"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.currentPageNumber").isNotEmpty())
                .andExpect(jsonPath("$.totalPageCount").isNotEmpty())
                .andExpect(jsonPath("$.totalResultCount").isNotEmpty())
                .andExpect(jsonPath("$.items").isEmpty());
    }
}
