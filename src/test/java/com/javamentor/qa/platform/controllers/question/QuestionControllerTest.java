package com.javamentor.qa.platform.controllers.question;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.javamentor.qa.platform.AbstractIntegrationTest;
import com.javamentor.qa.platform.dao.abstracts.model.QuestionDao;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


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

        mockMvc.perform(get("/api/question/order/new")
                .param("page", "1")
                .param("size", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.currentPageNumber").value(1))
                .andExpect(jsonPath("$.totalPageCount").isNotEmpty())
                .andExpect(jsonPath("$.totalResultCount").isNotEmpty())
                .andExpect(jsonPath("$.items").isNotEmpty())
                .andExpect(jsonPath("$.itemsOnPage").value( 10 ));
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
    public void shouldReturnEmptyPageCauseOfTooHighPage() throws Exception {

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

    @Test
    public void shouldSetTagForQuestionOneTag() throws Exception {

        List<Long> tagId = new ArrayList<>();
        tagId.add(new Long(1L));
        String jsonRequest = objectMapper.writeValueAsString(tagId);
        this.mockMvc.perform(MockMvcRequestBuilders
                .patch("/api/question/1/tag/add")
                .content(jsonRequest)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string("Tags were added"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldSetTagForQuestionWrongId() throws Exception {

        List<Long> tagId = new ArrayList<>();
        tagId.add(new Long(1L));
        String jsonRequest = objectMapper.writeValueAsString(tagId);
        this.mockMvc.perform(MockMvcRequestBuilders
                .patch("/api/question/10/tag/add")
                .content(jsonRequest)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string("Question not found"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldSetTagForQuestionFewTag() throws Exception {

        List<Long> tag = new ArrayList<>();
        tag.add(new Long(1));
        tag.add(new Long(2L));
        tag.add(new Long(3L));
        String jsonRequest = objectMapper.writeValueAsString(tag);
        this.mockMvc.perform(MockMvcRequestBuilders
                .patch("/api/question/1/tag/add")
                .content(jsonRequest)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string("Tags were added"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldSetTagForQuestionNoTag() throws Exception {

        List<Long> tag = new ArrayList<>();
        tag.add(new Long(6L));
        String jsonRequest = objectMapper.writeValueAsString(tag);
        this.mockMvc.perform(MockMvcRequestBuilders
                .patch("/api/question/1/tag/add")
                .content(jsonRequest)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string("Tag not found"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnErrorMessageBadParameterWrongSizeQuestionWithoutAnswer() throws Exception {
        mockMvc.perform(get("/api/question/order/new")
                        .param("page", "1")
                        .param("size", "0"))
                        .andDo(print())
                        .andExpect(status().is4xxClientError())
                        .andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8"))
                        .andExpect(content().string("Номер страницы и размер должны быть положительными. Максимальное количество записей на странице 100"));
    }

    @Test
    public void shouldReturnErrorMessageBadParameterWrongPageQuestionWithoutAnswer () throws Exception {
        mockMvc.perform(get("/api/question/order/new")
                .param("page", "0")
                .param("size", "2"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8"))
                .andExpect(content().string("Номер страницы и размер должны быть положительными. Максимальное количество записей на странице 100"));
    }

    @Test
    public void shouldReturnErrorMessageBadParameterMaxPageQuestionWithoutAnswer () throws Exception {
        mockMvc.perform(get("/api/question/order/new")
                .param("page", "2")
                .param("size", "200"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8"))
                .andExpect(content().string("Номер страницы и размер должны быть положительными. Максимальное количество записей на странице 100"));
    }

    @Test
    @DataSet (value = {"dataset/question/roleQuestionApi.yml",
            "dataset/question/usersQuestionApi.yml",
            "dataset/question/answerQuestionApi.yml",
            "dataset/question/questionQuestionApi.yml",
            "dataset/question/tagQuestionApi.yml",
            "dataset/question/question_has_tagQuestionApi.yml",
            "dataset/question/votes_on_question.yml"},
            useSequenceFiltering = true, cleanBefore = true, cleanAfter = true)
    public void shouldReturnOnePageQuestionsWithoutAnswer() throws Exception {

        this.mockMvc.perform(get("/api/question/withoutAnswer")
                .param("page", "1")
                .param("size", "1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currentPageNumber").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPageCount").value(7))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalResultCount").value(7))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items").isArray())
                .andDo(print())
                .andExpect(status().isOk());
    }
}