package com.javamentor.qa.platform.controllers.question;

import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.AbstractIntegrationTest;
import com.javamentor.qa.platform.dao.abstracts.model.QuestionDao;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DataSet(value = {"dataset/question/roleQuestionApi.yml",
        "dataset/question/usersQuestionApi.yml",
        "dataset/question/answerQuestionApi.yml",
        "dataset/question/questionQuestionApi.yml",
        "dataset/question/tagQuestionApi.yml",
        "dataset/question/question_has_tagQuestionApi.yml",
        "dataset/question/votes_on_question.yml"}, cleanBefore = true, cleanAfter = true)
class QuestionControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldGetLastTenQuestions() throws Exception {

        PageDto<QuestionDto, Object> expected = new PageDto<>();
        expected.setCurrentPageNumber(1);
        expected.setItemsOnPage(10);
        expected.setTotalPageCount(1);
        expected.setItemsOnPage(10);
        expected.setTotalResultCount(9);
        expected.setItems(new ArrayList<>());

        String resultContext = mockMvc.perform(get("/api/question/order/new")
                .param("page", "1")
                .param("size", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.currentPageNumber").value(1))
                .andExpect(jsonPath("$.totalPageCount").isNotEmpty())
                .andExpect(jsonPath("$.totalResultCount").isNotEmpty())
                .andExpect(jsonPath("$.items").isNotEmpty())
                .andExpect(jsonPath("$.itemsOnPage").value( 10 ))
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void shouldReturnErrorMessageBadParameterNegativePage() throws Exception {
        mockMvc.perform(get("/api/question/order/new")
                .param("page", "-1")
                .param("size", "10"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8"))
                .andExpect(content().string("Номер страницы и размер должны быть положительными. Максимальное количество записей на странице 100"));
    }

    @Test
    public void shouldReturnErrorMessageBadParameterNegativeSize() throws Exception {
        mockMvc.perform(get("/api/question/order/new")
                .param("page", "1")
                .param("size", "-1"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8"))
                .andExpect(content().string("Номер страницы и размер должны быть положительными. Максимальное количество записей на странице 100"));
    }
    @Test
    public void shouldReturnErrorMessageBadParameterZeroPage() throws Exception {
        mockMvc.perform(get("/api/question/order/new")
                .param("page", "0")
                .param("size", "10"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8"))
                .andExpect(content().string("Номер страницы и размер должны быть положительными. Максимальное количество записей на странице 100"));
    }

    @Test
    public void shouldReturnErrorMessageBadParameterZeroSize() throws Exception {
        mockMvc.perform(get("/api/question/order/new")
                .param("page", "1")
                .param("size", "0"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8"))
                .andExpect(content().string("Номер страницы и размер должны быть положительными. Максимальное количество записей на странице 100"));
    }

    @Test
    public void shouldReturnErrorMessageBadParameterTooBigSize() throws Exception {
        mockMvc.perform(get("/api/question/order/new")
                .param("page", "1")
                .param("size", "101"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8"))
                .andExpect(content().string("Номер страницы и размер должны быть положительными. Максимальное количество записей на странице 100"));
    }

    @Test
    public void shouldReturnEmptyPageCouseOfTooHighPage() throws Exception {

        PageDto<QuestionDto, Object> expected = new PageDto<>();
        expected.setCurrentPageNumber(100);
        expected.setItemsOnPage(10);
        expected.setTotalPageCount(1);
        expected.setItemsOnPage(10);
        expected.setTotalResultCount(9);

        List<QuestionDto> expectedItems = new ArrayList<>();
        expected.setItems(expectedItems);

        String resultContext = mockMvc.perform(get("/api/question/order/new")
                .param("page", "100")
                .param("size", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.currentPageNumber").isNotEmpty())
                .andExpect(jsonPath("$.totalPageCount").isNotEmpty())
                .andExpect(jsonPath("$.totalResultCount").isNotEmpty())
                .andExpect(jsonPath("$.items").isEmpty())
                .andReturn().getResponse().getContentAsString();

        PageDto<QuestionDao, Object> actual = objectMapper.readValue(resultContext, PageDto.class);

        Assert.assertEquals(expected.toString(), actual.toString());

    }

}
