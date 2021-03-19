package com.javamentor.qa.platform.controllers.question;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.AbstractIntegrationTest;
import com.javamentor.qa.platform.models.dto.*;
import com.javamentor.qa.platform.models.entity.question.CommentQuestion;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.QuestionViewed;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import com.javamentor.qa.platform.webapp.converters.AnswerConverter;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@DataSet(value = {"dataset/question/roleQuestionApi.yml",
        "dataset/question/usersQuestionApi.yml",
        "dataset/question/answerQuestionApi.yml",
        "dataset/question/questionQuestionApi.yml",
        "dataset/question/tagQuestionApi.yml",
        "dataset/question/question_has_tagQuestionApi.yml",
        "dataset/question/votes_on_question.yml",
        "dataset/comment/comment.yml",
        "dataset/comment/comment_question.yml",
        "dataset/question/question_viewed.yml",},
        cleanBefore = true)
@WithMockUser(username = "principal@mail.ru", roles = {"ADMIN", "USER"})
@ActiveProfiles("local")
class QuestionControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AnswerConverter answerConverter;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void shouldSetTagForQuestionOneTag() throws Exception {

        List<Long> tagId = new ArrayList<>();
        tagId.add(1L);
        String jsonRequest = objectMapper.writeValueAsString(tagId);
        this.mockMvc.perform(MockMvcRequestBuilders
                .patch("/api/question/13/tag/add")
                .content(jsonRequest)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string("Tags were added"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldSetTagForQuestionWrongId() throws Exception {

        List<Long> tagId = new ArrayList<>();
        tagId.add(1L);
        String jsonRequest = objectMapper.writeValueAsString(tagId);
        this.mockMvc.perform(MockMvcRequestBuilders
                .patch("/api/question/1111/tag/add")
                .content(jsonRequest)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string("Question not found"))
                .andExpect(status().isBadRequest());
    }


    @Test
    void shouldSetTagForQuestionFewTag() throws Exception {

        List<Long> tag = new ArrayList<>();
        tag.add(1L);
        tag.add(2L);
        tag.add(3L);
        String jsonRequest = objectMapper.writeValueAsString(tag);
        this.mockMvc.perform(MockMvcRequestBuilders
                .patch("/api/question/13/tag/add")
                .content(jsonRequest)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string("Tags were added"))
                .andExpect(status().isOk());

    }

    @Test
    void shouldSetTagForQuestionNoTag() throws Exception {

        List<Long> tag = new ArrayList<>();
        tag.add(11L);
        String jsonRequest = objectMapper.writeValueAsString(tag);
        this.mockMvc.perform(MockMvcRequestBuilders
                .patch("/api/question/13/tag/add")
                .content(jsonRequest)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string("Tag not found"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnErrorMessageBadParameterWrongSizeQuestionWithoutAnswer() throws Exception {
        mockMvc.perform(get("/api/question/order/new")
                .param("page", "1")
                .param("size", "0"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8"))
                .andExpect(content().string("Номер страницы и размер должны быть положительными. Максимальное количество записей на странице 100"));
    }

    @Test
    void shouldReturnErrorMessageBadParameterWrongPageQuestionWithoutAnswer() throws Exception {
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
    void shouldAddQuestionAnswerStatusOk() throws Exception {
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
    void shouldAddQuestionResponseBadRequestUserNotFound() throws Exception {
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
    void shouldAddQuestionResponseBadRequestTagsNotExist() throws Exception {
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
    void shouldAddAnswerToQuestionStatusOk() throws Exception {

        CreateAnswerDto createAnswerDto = new CreateAnswerDto();
        createAnswerDto.setHtmlBody("test answer");

        String jsonRequest = objectMapper.writeValueAsString(createAnswerDto);

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/question/14/answer")
                .contentType("application/json;charset=UTF-8")
                .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldAddAnswerToQuestionResponseStatusOk() throws Exception {
        CreateAnswerDto createAnswerDto = new CreateAnswerDto();
        createAnswerDto.setHtmlBody("test answer");

        String jsonRequest = objectMapper.writeValueAsString(createAnswerDto);

        String resultContext = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/question/14/answer")
                .contentType("application/json;charset=UTF-8")
                .content(jsonRequest))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body").value(createAnswerDto.getHtmlBody()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.questionId").value(14))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(153))
                .andReturn().getResponse().getContentAsString();

        AnswerDto answerDtoFromResponse = objectMapper.readValue(resultContext, AnswerDto.class);
        Answer answer = entityManager
                .createQuery("from Answer where id = :id", Answer.class)
                .setParameter("id", answerDtoFromResponse.getId())
                .getSingleResult();
        AnswerDto answerDtoFromDB = answerConverter.answerToAnswerDTO(answer);

        Assertions.assertEquals(answerDtoFromResponse.getBody(), answerDtoFromDB.getBody());
    }

    @Test
    void shouldAddAnswerToQuestionResponseBadRequestQuestionNotFound() throws Exception {

        CreateAnswerDto createAnswerDto = new CreateAnswerDto();
        createAnswerDto.setHtmlBody("test answer");

        String jsonRequest = objectMapper.writeValueAsString(createAnswerDto);

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/question/2222/answer")
                .contentType("application/json;charset=UTF-8")
                .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Question not found"));
    }

    @Test
    void shouldGetAnswersListFromQuestionStatusOk() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/question/10/answer")
                .contentType("application/json;charset=UTF-8")
                .param("page", "1")
                .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetAnswersListFromQuestionResponseStatusOk() throws Exception {

        String resultContext = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/question/10/answer")
                .param("page", "1")
                .param("size", "10"))
                .andReturn().getResponse().getContentAsString();

        List<AnswerDto> answerDtoListFromResponse = objectMapper.readValue(resultContext, new TypeReference<List<AnswerDto>>() {
        });
        List<AnswerDto> answerList = (List<AnswerDto>) entityManager
                .createQuery("SELECT new com.javamentor.qa.platform.models.dto.AnswerDto(a.id, u.id, q.id, " +
                        "a.htmlBody, a.persistDateTime, a.isHelpful, a.dateAcceptTime, " +
                        "(SELECT COUNT(av.answer.id) FROM VoteAnswer AS av WHERE av.answer.id = a.id), " +
                        "u.imageLink, u.fullName) " +
                        "FROM Answer as a " +
                        "INNER JOIN a.user as u " +
                        "JOIN a.question as q " +
                        "WHERE q.id = :questionId")
                .setParameter("questionId", 10L)
                .getResultList();

        Assertions.assertEquals(answerDtoListFromResponse, answerList);
    }

    @Test
    void shouldGetAnswersListFromQuestionResponseBadRequestQuestionNotFound() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/question/2222/answer")
                .contentType("application/json;charset=UTF-8")
                .param("page", "1")
                .param("size", "10"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Question not found"));
    }

    @Test
    void shouldReturnQuestionsWithGivenTags() throws Exception {
        Long a[] = {1L, 3L, 5L};
        List<Long> tagIds = Arrays.stream(a).collect(Collectors.toList());
        String jsonRequest = objectMapper.writeValueAsString(tagIds);

        String resultContext = mockMvc.perform(MockMvcRequestBuilders.get("/api/question/withTags")
                .content(jsonRequest)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .param("page", "1")
                .param("size", "3")
                .param("tagIds", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currentPageNumber").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPageCount").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalResultCount").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.itemsOnPage").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items").isNotEmpty())
                .andReturn().getResponse().getContentAsString();

        PageDto<LinkedHashMap, Object> actual = objectMapper.readValue(resultContext, PageDto.class);

        int numberOfItemsOnPage = actual.getItems().size();
        Assertions.assertEquals(3, numberOfItemsOnPage);
    }

    @Test
    void getQuestionSearchWithStatusOk() throws Exception {
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
    void getQuestionSearchWithSearchMessageNull() throws Exception {
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
    void getQuestionSearchWithSearchMessageIsEmpty() throws Exception {
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
    void getQuestionSearchWithSearchByAuthor() throws Exception {
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
        tag.add(new TagDto(5L, "sql"));

        List<QuestionDto> items = new ArrayList<>();
        items.add(new QuestionDto(
                18L,
                "Question number seven",
                3L, "Tot", null,
                "Changes made in sql query in excel reflects changes only on single sheet",
                1, 3, 1,
                LocalDateTime.of(2020, 01, 02, 00, 00, 00),
                LocalDateTime.of(2020, 05, 02, 13, 58, 56), tag));

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

    @Test
    void shouldReturnErrorMessageBadParameterMaxPageQuestionWithoutAnswer() throws Exception {
        mockMvc.perform(get("/api/question/order/new")
                .param("page", "2")
                .param("size", "200"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8"))
                .andExpect(content().string("Номер страницы и размер должны быть положительными. Максимальное количество записей на странице 100"));
    }

    @Test
    void testPaginationQuestionsWithoutAnswer() throws Exception {

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
    @DataSet(value = {"dataset/question/roleQuestionApi.yml",
            "dataset/question/usersQuestionApi.yml",
            "dataset/question/answerQuestionApi.yml",
            "dataset/question/questionQuestionApi.yml",
            "dataset/question/tagQuestionApi.yml",
            "dataset/question/question_has_tagQuestionApi.yml",
            "dataset/question/votes_on_question.yml"},
            useSequenceFiltering = true, cleanBefore = true, cleanAfter = true)
    void testIsQuestionWithoutAnswers() throws Exception {

        LocalDateTime persistDateTime = LocalDateTime.of(LocalDate.of(2020, 1, 2), LocalTime.of(0, 0, 0));
        LocalDateTime lastUpdateDateTime = LocalDateTime.of(LocalDate.of(2020, 2, 1), LocalTime.of(13, 58, 56));

        PageDto<QuestionDto, Object> expectPage = new PageDto<>();
        expectPage.setCurrentPageNumber(1);
        expectPage.setItemsOnPage(1);
        expectPage.setTotalPageCount(7);
        expectPage.setTotalResultCount(7);

        List<TagDto> tagsList = new ArrayList<>();
        tagsList.add(new TagDto(1L, "java"));

        List<QuestionDto> itemsList = new ArrayList<>();
        itemsList.add(new QuestionDto(14L,
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
        Assertions.assertEquals(expectPage.toString(), actualPage.toString());
    }

    @Test
    void shouldReturnQuestionsWithoutSpecifiedTags() throws Exception {

        List<Long> withoutTagIds = new ArrayList<>();
        withoutTagIds.add(1L);
        withoutTagIds.add(2L);
        withoutTagIds.add(5L);

        String jsonWithoutTagIds = objectMapper.writeValueAsString(withoutTagIds);

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/question/withoutTags")
                .param("page", "1")
                .param("size", "10")
                .contentType("application/json;charset=UTF-8")
                .content(jsonWithoutTagIds))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.currentPageNumber").value(1))
                .andExpect(jsonPath("$.totalPageCount").value(1))
                .andExpect(jsonPath("$.totalResultCount").value(1))
                .andExpect(jsonPath("$.items[*].listTagDto[*].id", Matchers.hasItem(3)))
                .andExpect(jsonPath("$.items[*].listTagDto[*].id", Matchers.not(1)))
                .andExpect(jsonPath("$.items[*].listTagDto[*].id", Matchers.not(2)))
                .andExpect(jsonPath("$.items[*].listTagDto[*].id", Matchers.not(5)))
                .andExpect(jsonPath("$.itemsOnPage").value(10));
    }

    @Test
    void shouldReturnEmptyPaginationIfTagIdsMissing() throws Exception {

        List<Long> withoutTagIds = new ArrayList<>();

        String jsonWithoutTagIds = objectMapper.writeValueAsString(withoutTagIds);

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/question/withoutTags")
                .param("page", "1")
                .param("size", "10")
                .contentType("application/json;charset=UTF-8")
                .content(jsonWithoutTagIds))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.currentPageNumber").value(1))
                .andExpect(jsonPath("$.totalPageCount").value(0))
                .andExpect(jsonPath("$.totalResultCount").value(0))
                .andExpect(jsonPath("$.items").isEmpty())
                .andExpect(jsonPath("$.itemsOnPage").value(10));
    }


    @Test
    void voteUpStatusOk() throws Exception {

        List<VoteAnswer> before = entityManager.createNativeQuery("select * from votes_on_answers").getResultList();
        int first = before.size();

        this.mockMvc.perform(MockMvcRequestBuilders
                .patch("/api/question/10/answer/51/upVote")
                .contentType("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.userId").isNumber())
                .andExpect(jsonPath("$.answerId").isNumber())
                .andExpect(jsonPath("$.persistDateTime").isNotEmpty())
                .andExpect(jsonPath("$.vote").isNumber());

        List<VoteAnswer> after = entityManager.createNativeQuery("select * from votes_on_answers").getResultList();
        int second = after.size();
        Assertions.assertEquals(first + 1, second);
    }

    @Test
    void voteUpQuestionIsNotExist() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                .patch("/api/question/1/answer/1/upVote")
                .contentType("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8"))
                .andExpect(content().string("Question was not found"));

    }

    @Test
    void voteUpAnswerIsNotExist() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                .patch("/api/question/10/answer/4/upVote")
                .contentType("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8"))
                .andExpect(content().string("Answer was not found"));
    }

    @Test
    void voteDownStatusOk() throws Exception {

        List<VoteAnswer> before = entityManager.createNativeQuery("select * from votes_on_answers").getResultList();
        int first = before.size();

        this.mockMvc.perform(MockMvcRequestBuilders
                .patch("/api/question/10/answer/51/downVote")
                .contentType("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.userId").isNumber())
                .andExpect(jsonPath("$.answerId").isNumber())
                .andExpect(jsonPath("$.persistDateTime").isNotEmpty())
                .andExpect(jsonPath("$.vote").isNumber());

        List<VoteAnswer> after = entityManager.createNativeQuery("select * from votes_on_answers").getResultList();
        int second = after.size();
        Assertions.assertEquals(first + 1, second);
    }

    @Test
    void voteDownQuestionIsNotExist() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                .patch("/api/question/1/answer/1/downVote")
                .contentType("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8"))
                .andExpect(content().string("Question was not found"));

    }

    @Test
    void voteDownAnswerIsNotExist() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                .patch("/api/question/10/answer/4/downVote")
                .contentType("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8"))
                .andExpect(content().string("Answer was not found"));
    }

    @Test
    void shouldAddCommentToQuestionResponseBadRequestQuestionNotFound() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/question/9999/comment")
                .content("This is very good question!")
                .accept(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8"))
                .andExpect(content().string("Question not found"));
    }

    @Test
    void shouldAddCommentToQuestionResponseCommentDto() throws Exception {
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/question/10/comment")
                .content("This is very good question!")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value("This is very good question!"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.persistDate", org.hamcrest.Matchers.containsString(LocalDate.now().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastRedactionDate", org.hamcrest.Matchers.containsString(LocalDate.now().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.commentType").value("QUESTION"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(153))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("Uou"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.reputation").value(2))
                .andReturn();

        JSONObject dto = new JSONObject(result.getResponse().getContentAsString());

        List<CommentQuestion> resultList = entityManager.createNativeQuery("select * from comment_question where comment_id = " + dto.get("id")).getResultList();
        Assertions.assertFalse(resultList.isEmpty());
    }

    @Test
    void getCommentListByQuestionIdWithStatusOk() throws Exception {

        //тестируем контроллер, получаем лист CommentQuestionDto
        String resultContext = this.mockMvc.perform(get("/api/question/10/comments"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<CommentQuestionDto> commentQuestionDtoFromResponseList = objectMapper.readValue(resultContext, List.class);

        Assertions.assertTrue(!commentQuestionDtoFromResponseList.isEmpty());

        //вытаскиваем из БД количество комментариев у указанного вопроса
        Query queryToCommentQuestionTable = entityManager.createNativeQuery("select count(*) from comment_question where question_id = ?");
        queryToCommentQuestionTable.setParameter(1, 10);
        BigInteger count = (BigInteger) queryToCommentQuestionTable.getSingleResult();

        Assertions.assertEquals(commentQuestionDtoFromResponseList.size(), count.intValue());
    }

    @Test
    void getCommentListByQuestionIdWithStatusQuestionNotFound() throws Exception {

        Question question = null;
        try {
            question = entityManager
                    .createQuery("from Question where id = :id", Question.class)
                    .setParameter("id", 130L)
                    .getSingleResult();
        } catch (NoResultException nre) {
            //ignore
        }

        this.mockMvc.perform(get("/api/question/130/comments"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Question not found"));

        Assertions.assertNull(question);
    }

    @Test
    void shouldCreateVoteQuestionUp() throws Exception {

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/question/19/upVote")).andReturn();

        JSONObject jsonObject = new JSONObject(result.getResponse().getContentAsString());

        Assertions.assertEquals(1, jsonObject.get("vote"));
    }

    @Test
    void shouldCreateVoteQuestionDown() throws Exception {

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/question/19/downVote")).andReturn();

        JSONObject jsonObject = new JSONObject(result.getResponse().getContentAsString());

        Assertions.assertEquals(jsonObject.get("vote"), -1);
    }

    @Test
    void AddQuestionAsViewedStatusOk() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/question/14/view"))
                .andExpect(status().isOk());
    }


    @Test
    void AddQuestionAsViewedIfSecondRequest() throws Exception {

        //считаем имеющиеся записи в БД
        Query queryBefore = entityManager.createNativeQuery("select * from question_viewed where user_id = 153");
        List<QuestionViewed> before = queryBefore.getResultList();
        int countBefore = before.size();

        //добавляем еще одну уникальную
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/question/14/view"))
                .andExpect(status().isOk());

        //считаем повторно и сравниваем количество записей
        Query queryAfter = entityManager.createNativeQuery("select * from question_viewed where user_id = 153");
        Assertions.assertEquals(countBefore + 1, queryAfter.getResultList().size());


    }

    @Test
    void AddQuestionAsViewedIfSecondEqualRequest() throws Exception {

        //вносим первую уникальную запись
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/question/15/view"))
                .andExpect(status().isOk());
        Query query = entityManager.createNativeQuery("select * from question_viewed where user_id = 153 and " +
                "question_id = ?", QuestionViewed.class);
        query.setParameter(1, 15L);
        //считываем внесенную запись
        QuestionViewed questionViewedFirst = (QuestionViewed) query.getSingleResult();

        //считаем количество записей в БД
        Query queryBefore = entityManager.createNativeQuery("select * from question_viewed where user_id = 153",
                QuestionViewed.class);
        List<QuestionViewed> before = queryBefore.getResultList();
        int countBefore = before.size();

        //вносим запись повторно
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/question/10/view"))
                .andExpect(status().isOk());
        //считаем количество записей повторно и сравниваем
        Query queryDoubleRequestAfter = entityManager.createNativeQuery("select * from question_viewed where " +
                "user_id = 153", QuestionViewed.class);
        int countAfter = queryDoubleRequestAfter.getResultList().size();
        Assertions.assertEquals(countBefore, countAfter);

        //проверяем изменилась ли запись после попытки ее повторного внесения?
        Query query2 = entityManager.createNativeQuery("select * from question_viewed where user_id = 153 and " +
                "question_id = ?", QuestionViewed.class);
        query2.setParameter(1, 15L);
        QuestionViewed questionViewedSecond = (QuestionViewed) query2.getSingleResult();
        Assertions.assertEquals(questionViewedFirst.getId(), questionViewedSecond.getId());
        Assertions.assertEquals(questionViewedFirst.getLocalDateTime(), questionViewedSecond.getLocalDateTime());
        Assertions.assertEquals(questionViewedFirst.getUser().getId(), questionViewedSecond.getUser().getId());
        Assertions.assertEquals(questionViewedFirst.getQuestion().getId(), questionViewedSecond.getQuestion().getId());
    }


    @Test
    void AddQuestionAsViewedIsNotExist() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/question/21/view"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8"))
                .andExpect(content().string("Question not found"));
    }

    @Test
    void getQuestionDtoWithoutVotes() throws Exception {
        String resultContext = this.mockMvc.perform(get("/api/question/13"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        QuestionDto questionDto = objectMapper.readValue(resultContext, QuestionDto.class);

        Assertions.assertEquals(0,questionDto.getCountValuable());
    }


}