package com.javamentor.qa.platform.controllers.question;

import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.AbstractIntegrationTest;
import com.javamentor.qa.platform.dao.abstracts.model.QuestionDao;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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
    void getAllDto() throws Exception {
        System.out.println("test");
    }

    @Test
    public void shouldGetLastTenQuestions() throws Exception {

        PageDto<QuestionDto, Object> expected = new PageDto<>();
        expected.setCurrentPageNumber(1);
        expected.setItemsOnPage(10);
        expected.setTotalPageCount(1);
        expected.setItemsOnPage(10);
        expected.setTotalResultCount(9);

        LocalDateTime fromDateAndTime = LocalDateTime.now(ZoneOffset.UTC).truncatedTo(ChronoUnit.SECONDS);

        List<QuestionDto> expectedItems = new ArrayList<>();
        {
            expectedItems.add(new QuestionDto(9L, "Question number nine", 2L, "Tot",
                    null, "Conceptual explanation: why COUNT() syntax doesn't work on SQLZoo JOIN Lesson Challenge 13?",
                    1, 3, 1,
                    fromDateAndTime,
                    LocalDateTime.of(LocalDate.of(2020, 5, 4), LocalTime.of(13, 58, 56)),
                    Arrays.asList(new TagDto[]{new TagDto(5L, "sql")})));

            expectedItems.add(new QuestionDto(8L, "Question number eight", 3L, "Tot",
                    null, "can someone Explain why the following query will return an error message?",
                    1, 3, 1,
                    fromDateAndTime,
                    LocalDateTime.of(LocalDate.of(2020, 5, 3), LocalTime.of(13, 58, 56)),
                    Arrays.asList(new TagDto[]{new TagDto(5L, "sql")})));

            expectedItems.add(new QuestionDto(7L, "Question number seven", 3L, "Tot",
                    null, "Changes made in sql query in excel reflects changes only on single sheet",
                    1, 3, 1,
                    fromDateAndTime,
                    LocalDateTime.of(LocalDate.of(2020, 5, 2), LocalTime.of(13, 58, 56)),
                    Arrays.asList(new TagDto[]{new TagDto(5L, "sql")})));


            fromDateAndTime = fromDateAndTime.minusDays(1L);
            expectedItems.add(new QuestionDto(6L, "Question number six", 2L, "Tot",
                    null, "Glightbox problem in mobile horizontal scroll",
                    1, 3, 1,
                    fromDateAndTime,
                    LocalDateTime.of(LocalDate.of(2020, 5, 1), LocalTime.of(13, 58, 56)),
                    Arrays.asList(new TagDto[]{new TagDto(2L, "javaScript")})));

            fromDateAndTime = fromDateAndTime.minusDays(9L);
            expectedItems.add(new QuestionDto(4L, "Question number four", 2L, "Tot",
                    null, "Chart js drill down chart",
                    2, 3, 1,
                    fromDateAndTime,
                    LocalDateTime.of(LocalDate.of(2020, 2, 2), LocalTime.of(13, 58, 56)),
                    Arrays.asList(new TagDto[]{new TagDto(2L, "javaScript")})));

            fromDateAndTime = fromDateAndTime.plusDays(5L);
            expectedItems.add(new QuestionDto(5L, "Question number five", 1L, "Teat",
                    null, "Save web page who as exit",
                    2, 3, 1,
                    fromDateAndTime,
                    LocalDateTime.of(LocalDate.of(2020, 2, 1), LocalTime.of(13, 58, 56)),
                    Arrays.asList(new TagDto[]{new TagDto(3L, "html")})));

            fromDateAndTime = fromDateAndTime.minusDays(3L);
            expectedItems.add(new QuestionDto(3L, "Question number three", 2L, "Tot",
                    null, "Swagger - add â\u0080\u009Cpath variableâ\u0080\u009D in request url",
                    2, 3, 1,
                    fromDateAndTime,
                    LocalDateTime.of(LocalDate.of(2020, 2, 1), LocalTime.of(13, 58, 56)),
                    Arrays.asList(new TagDto[]{new TagDto(1L, "java")})));

            fromDateAndTime = fromDateAndTime.minusDays(6L);
            expectedItems.add(new QuestionDto(2L, "Question number two", 1L, "Teat",
                    null, "while using java timer, error that The constructor Timer(int, ActionListener) is undefined",
                    5, 3, 1,
                    fromDateAndTime,
                    LocalDateTime.of(LocalDate.of(2020, 1, 20), LocalTime.of(13, 58, 56)),
                    Arrays.asList(new TagDto[]{new TagDto(1L, "java")})));

            fromDateAndTime = fromDateAndTime.minusDays(6L);
            expectedItems.add(new QuestionDto(1L, "Question number one", 1L, "Teat",
                    null, "Stream filter on list keeping some of the filtered values",
                    3, 3, 1,
                    fromDateAndTime,
                    LocalDateTime.of(LocalDate.of(2020, 1, 2), LocalTime.of(13, 58, 56)),
                    Arrays.asList(new TagDto[]{new TagDto(1L, "java")})));
        }
        expected.setItems(expectedItems);

        String resultContext = mockMvc.perform(get("/api/question/order/new")
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

        PageDto<QuestionDao, Object> actual = objectMapper.readValue(resultContext, PageDto.class);
        Assert.assertEquals(expected.toString(), actual.toString());
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
