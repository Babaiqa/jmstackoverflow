package com.javamentor.qa.platform.controllers.question;

import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.AbstractIntegrationTest;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DataSet(value = {"dataset/question/roleQuestionApi.yml",
        "dataset/question/usersQuestionApi.yml",
        "dataset/question/answerQuestionApi.yml",
        "dataset/question/questionQuestionApi.yml",
        "dataset/question/tagQuestionApi.yml",
        "dataset/question/question_has_tagQuestionApi.yml",
        "dataset/question/votes_on_question.yml"},
        useSequenceFiltering = true, cleanBefore = true, cleanAfter = true)



class QuestionControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllDto() throws Exception {
        System.out.println("test");
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
        tag.add(new Long(1L));
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
    void shouldAddQuestionStatusOk() throws Exception {

        QuestionCreateDto questionCreateDto = new QuestionCreateDto();
        questionCreateDto.setUserId(2L);
        questionCreateDto.setTitle("Question number one1");
        questionCreateDto.setDescription("Question Description493");
        List<TagDto> listTagsAdd = new ArrayList<>();
        listTagsAdd.add(new TagDto(5L, "Structured Query Language"));
        questionCreateDto.setTags(listTagsAdd);

        String jsonRequest = objectMapper.writeValueAsString(questionCreateDto);

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/question/add")
                .contentType("application/json;charset=UTF-8")
                .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    public  void shouldAddQuestionResponseStatusOk() throws Exception {
        QuestionCreateDto questionCreateDto = new QuestionCreateDto();
        questionCreateDto.setUserId(1L);
        questionCreateDto.setTitle("Question number one1");
        questionCreateDto.setDescription("Question Description493");
        List<TagDto> listTagsAdd = new ArrayList<>();
        listTagsAdd.add(new TagDto(5L, "Structured Query Language"));
        questionCreateDto.setTags(listTagsAdd);

        String jsonRequest = objectMapper.writeValueAsString(questionCreateDto);

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/question/add")
                .contentType("application/json;charset=UTF-8")
                .content(jsonRequest))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Question number one1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Question Description493"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorId").value(1));
    }


    @Test
    public  void shouldAddQuestionResponseBadRequestUserNotFound() throws Exception {
        QuestionCreateDto questionCreateDto = new QuestionCreateDto();
        questionCreateDto.setUserId(2222L);
        questionCreateDto.setTitle("Question number one1");
        questionCreateDto.setDescription("Question Description493");
        List<TagDto> listTagsAdd = new ArrayList<>();
        listTagsAdd.add(new TagDto(5L, "Structured Query Language"));
        questionCreateDto.setTags(listTagsAdd);

        String jsonRequest = objectMapper.writeValueAsString(questionCreateDto);

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/question/add")
                .contentType("application/json;charset=UTF-8")
                .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("questionCreateDto.userId dont`t exist"));
    }


    @Test
    public  void shouldAddQuestionResponseBadRequestTagsNotExist() throws Exception {
        QuestionCreateDto questionCreateDto = new QuestionCreateDto();
        questionCreateDto.setUserId(1L);
        questionCreateDto.setTitle("Question number one1");
        questionCreateDto.setDescription("Question Description493");
        questionCreateDto.setTags(null);

        String jsonRequest = objectMapper.writeValueAsString(questionCreateDto);

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/question/add")
                .contentType("application/json;charset=UTF-8")
                .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("addQuestion.questionCreateDto.tags: Значение tags должно быть заполнено"));
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
    public void testPaginationQuestionsWithoutAnswer() throws Exception {

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

    @Test
    public void testIsQuestionWithoutAnswers () throws Exception {

        LocalDateTime persistDateTime = LocalDateTime.of(LocalDate.of(2020, 2, 1), LocalTime.of(13, 58, 56));
        LocalDateTime lastUpdateDateTime = LocalDateTime.of(LocalDate.of(2020, 2, 1), LocalTime.of(13, 58, 56));

        PageDto<QuestionDto, Object> expectPage = new PageDto<>();
        expectPage.setCurrentPageNumber(1);
        expectPage.setItemsOnPage(1);
        expectPage.setTotalPageCount(7);
        expectPage.setTotalResultCount(7);

        List<TagDto> tagsList = new ArrayList<>();
        tagsList.add(new TagDto(1L, "java"));

        List<QuestionDto> itemsList = new ArrayList<>();
        itemsList.add(new QuestionDto(3L,
                "Question number three",
                2L,
                "Tot",
                null,
                "Swagger - add \"path variable\" in request url",
                2,
                3,
                1,
                persistDateTime,
                lastUpdateDateTime,
                tagsList));

        expectPage.setItems(itemsList);

        String result = mockMvc.perform(get("/api/question/withoutAnswer")
        .param("page", "1")
        .param("size", "1")
        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        PageDto<QuestionDto, Object> actualPage = objectMapper.readValue(result, PageDto.class);
        Assert.assertEquals(expectPage.toString(), actualPage.toString());
    }
}