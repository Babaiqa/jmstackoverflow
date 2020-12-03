package com.javamentor.qa.platform.controllers.question;

import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.AbstractIntegrationTest;
import com.javamentor.qa.platform.models.dto.*;
import com.javamentor.qa.platform.models.entity.question.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    public void getQuestionSearchWithStatusOk() throws Exception {
        QuestionSearchDto questionSearchDto = new QuestionSearchDto("sql query in excel");
        String json = objectMapper.writeValueAsString(questionSearchDto);

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/question/search")
                .param("page", "1")
                .param("size", "5")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getQuestionSearchWithSearchMessageNull() throws Exception {
        QuestionSearchDto questionSearchDto = new QuestionSearchDto(null);
        String json = objectMapper.writeValueAsString(questionSearchDto);

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/question/search")
                .param("page", "1")
                .param("size", "5")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getQuestionSearchWithSearchMessageIsEmpty() throws Exception {
        QuestionSearchDto questionSearchDto = new QuestionSearchDto("");
        String json = objectMapper.writeValueAsString(questionSearchDto);

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/question/search")
                .param("page", "1")
                .param("size", "5")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getQuestionSearchWithSearchByAuthor() throws Exception {
        QuestionSearchDto questionSearchDto = new QuestionSearchDto("author:3");
        String json = objectMapper.writeValueAsString(questionSearchDto);

        PageDto<QuestionDto, Object> expect = new PageDto<>();
        expect.setTotalResultCount(2);
        expect.setItemsOnPage(1);
        expect.setCurrentPageNumber(1);
        expect.setTotalPageCount(2);
        expect.setItemsOnPage(1);
        expect.setMeta(null);

        List<TagDto> tag = new ArrayList<>();
        tag.add(new TagDto(5L,"sql"));

        List<QuestionDto> items = new ArrayList<>();
        items.add(new QuestionDto(
                7L,
                "Question number seven",
                3L, "Tot", null,
                "Changes made in sql query in excel reflects changes only on single sheet",
                1,3,1,
                LocalDateTime.of(2020,01,02,00,00, 00),
                LocalDateTime.of(2020,05,02,13,58, 56),tag));

        expect.setItems(items);

        String jsonResult = objectMapper.writeValueAsString(expect);

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/question/search")
                .param("page", "1")
                .param("size", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(jsonResult));
    }
}
