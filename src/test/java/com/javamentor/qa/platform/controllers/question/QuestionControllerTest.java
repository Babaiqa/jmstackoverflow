package com.javamentor.qa.platform.controllers.question;

import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.AbstractIntegrationTest;
import com.javamentor.qa.platform.dao.abstracts.model.QuestionDao;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

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
    public void shouldAddQuestionResponseStatusOk() throws Exception {
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
    public void shouldAddQuestionResponseBadRequestUserNotFound() throws Exception {
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
    public void shouldAddQuestionResponseBadRequestTagsNotExist() throws Exception {
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
    public void shouldReturnQuestionsWithGivenTags() throws Exception {
        Long a[] = {1L, 3L, 5L};
        List<Long> tagIds = Arrays.stream(a).collect(Collectors.toList());
        String jsonRequest = objectMapper.writeValueAsString(tagIds);

        PageDto<QuestionDto, Object> expected = new PageDto<>();

        expected.setCurrentPageNumber(1);
        expected.setItemsOnPage(3);
        expected.setTotalPageCount(1);
        expected.setTotalResultCount(3);
        ArrayList<QuestionDto> questionDtos = new ArrayList<>();
        expected.setItems(questionDtos);

        String resultContext = mockMvc.perform(MockMvcRequestBuilders.get("/api/question/withTags")
                .content(jsonRequest)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .param("page", "1")
                .param("size", "3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currentPageNumber").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPageCount").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalResultCount").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.items").isNotEmpty())
                .andReturn().getResponse().getContentAsString();

        PageDto<LinkedHashMap, Object> actual = objectMapper.readValue(resultContext, PageDto.class);

        LinkedHashMap<String, Object> linkedHashMap  = actual.getItems().get(0);

        ArrayList<LinkedHashMap<String, Object>> getMap = (ArrayList<LinkedHashMap<String, Object>>) linkedHashMap.get("listTagDto");

        Integer integer = (Integer) getMap.get(0).get("id");

        Assert.assertTrue(  integer == 1 );

        linkedHashMap  = actual.getItems().get(1);

        getMap = (ArrayList<LinkedHashMap<String, Object>>) linkedHashMap.get("listTagDto");

        integer = (Integer) getMap.get(0).get("id");

        Assert.assertTrue(  integer == 1 );

        linkedHashMap  = actual.getItems().get(2);

        getMap = (ArrayList<LinkedHashMap<String, Object>>) linkedHashMap.get("listTagDto");

        integer = (Integer) getMap.get(0).get("id");

        Assert.assertTrue(  integer == 3 );
    }

}
