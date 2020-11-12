package com.javamentor.qa.platform.controllers.user;

import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.AbstractIntegrationTest;
import com.javamentor.qa.platform.models.dto.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DataSet(value = "dataset/user/userApi.yml", disableConstraints = true, cleanBefore = true, cleanAfter = true)
    public void shouldGetUserById() throws Exception {
        UserDto user = new UserDto();
        user.setId(1L);
        user.setEmail("ivanov@mail.ru");
        user.setFullName("Teat");
        user.setLinkImage("https://www.google.com/search?q=D0");
        user.setReputation(2);

        this.mockMvc.perform(get("/api/user/" + user.getId()))
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("id").value(user.getId()))
                .andExpect(jsonPath("email").value(user.getEmail()))
                .andExpect(jsonPath("fullName").value(user.getFullName()))
                .andExpect(jsonPath("linkImage").value(user.getLinkImage()))
                .andExpect(jsonPath("reputation").value(user.getReputation()))
                .andExpect(status().isOk());
    }
    @Test
    @DataSet(value = "dataset/question/usersQuestionApi.yml", disableConstraints = true, cleanBefore = true, cleanAfter = true)
    public void shouldGetUserByIsNot() throws Exception {
        int id = 4;
        this.mockMvc.perform(get("/api/user/" + id))
                .andDo(print())
                .andExpect(content().string("User with id " + id + " not found"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DataSet(value = "dataset/user/roleUserApi.yml", disableConstraints = true, cleanBefore = true, cleanAfter = true)
    public void shouldCreateUser() throws Exception {
        UserRegistrationDto user = new UserRegistrationDto();
        user.setEmail("11@22.ru");
        user.setPassword("100");
        user.setFullName("Ivan Ivanich");

        String jsonRequest = objectMapper.writeValueAsString(user);

        this.mockMvc.perform(post("/api/user/registration").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("email").value(user.email))
                .andExpect(jsonPath("fullName").value(user.fullName))
                .andExpect(jsonPath("linkImage").isEmpty())
                .andExpect(jsonPath("reputation").value(0))
                .andExpect(status().isOk());
    }

    @Test
    @DataSet(value = {"dataset/user/userApi.yml", "dataset/user/roleUserApi.yml"}, disableConstraints = true, cleanBefore = true, cleanAfter = true)
    public void shouldCreateUserIsNot() throws Exception {
        UserRegistrationDto user = new UserRegistrationDto();
        user.setEmail("ivanov@mail.ru");
        user.setPassword("100");
        user.setFullName("Ivan Ivanich");
        String jsonRequest = objectMapper.writeValueAsString(user);
        this.mockMvc.perform(post("/api/user/registration").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string("User with email " + user.getEmail() + " already exist"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldCreateUserValidateEmail() throws Exception {
        UserRegistrationDto user = new UserRegistrationDto();
        user.setEmail("ivanovmail.ru");
        user.setPassword("100");
        user.setFullName("Ivan Ivanich");
        String jsonRequest = objectMapper.writeValueAsString(user);
        this.mockMvc.perform(post("/api/user/registration").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string("createUser.userRegistrationDto.email: Заданный email не может существовать"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DataSet(value = "dataset/user/userUserApi.yml", disableConstraints = true, cleanBefore = true, cleanAfter = true)
    public void shouldGetUserByName() throws Exception {
        PageDto<UserDtoList, Object> expected = new PageDto<>();
        expected.setCurrentPageNumber(1);
        expected.setTotalPageCount(1);
        expected.setTotalResultCount(3);
        expected.setItemsOnPage(10);
//---------------------------------------------------------------


    List<UserDtoList> expectedItems = new ArrayList<>();

    expectedItems.add(new UserDtoList(1L, "Teat", "linkImage1", 2, Arrays.asList(new TagDto[]{})));
    expectedItems.add(new UserDtoList(2L, "Teat", "linkImage2", 1, Arrays.asList(new TagDto[]{})));
    expectedItems.add(new UserDtoList(4L, "Tob", "linkImage4", 4, Arrays.asList(new TagDto[]{})));
    expected.setItems(expectedItems);

    String resultContext = mockMvc.perform(get("/api/user/find")
            .param("name", "t")
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
    Assert.assertEquals(expected.toString(), actual.toString());
}

    @Test
    @DataSet(value = "dataset/user/userUserApi.yml", disableConstraints = true, cleanBefore = true, cleanAfter = true)
    public void inabilityGetUserByNameWithWrongName() throws Exception {
        this.mockMvc.perform(get("/api/user/find")
                .param("name", "c")
                .param("page", "1")
                .param("size", "10"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8"))
                .andExpect(content().string("User with this name does not exist"));
    }

    @Test
    @DataSet(value = "dataset/user/userUserApi.yml", disableConstraints = true, cleanBefore = true, cleanAfter = true)
    public void inabilityGetUserByNameWithNegativePage() throws Exception {
        this.mockMvc.perform(get("/api/user/find")
                .param("name", "t")
                .param("page", "-1")
                .param("size", "10"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8"))
                .andExpect(content().string("The page number and size must be positive. Maximum number of records per page 100"));
    }

    @Test
    @DataSet(value = "dataset/user/userUserApi.yml", disableConstraints = true, cleanBefore = true, cleanAfter = true)
    public void inabilityGetUserByNameWithZeroSize() throws Exception {
        this.mockMvc.perform(get("/api/user/find")
                .param("name", "t")
                .param("page", "1")
                .param("size", "0"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8"))
                .andExpect(content().string("The page number and size must be positive. Maximum number of records per page 100"));
    }

    @Test
    @DataSet(value = "dataset/user/userUserApi.yml", disableConstraints = true, cleanBefore = true, cleanAfter = true)
    public void inabilityGetUserByNameWithNegativeSize() throws Exception {
        this.mockMvc.perform(get("/api/user/find")
                .param("name", "t")
                .param("page", "1")
                .param("size", "-1"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8"))
                .andExpect(content().string("The page number and size must be positive. Maximum number of records per page 100"));
    }

    @Test
    @DataSet(value = "dataset/user/userUserApi.yml", disableConstraints = true, cleanBefore = true, cleanAfter = true)
    public void inabilityGetUserByNameOnPageNotExists() throws Exception {
        this.mockMvc.perform(get("/api/user/find")
                .param("name", "t")
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


//---------------------------------------------------------------
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
        expectedItems.add(new UserDtoList(1L, "Teat", null, 0, Arrays.asList(new TagDto[]{new TagDto(1L,"java"), new TagDto(3L,"html")})));
        expectedItems.add(new UserDtoList(2L, "Tot", null, 0, Arrays.asList(new TagDto[]{new TagDto(2L,"javaScript"), new TagDto(1L,"java"), new TagDto(5L,"sql")})));
        expectedItems.add(new UserDtoList(3L, "Tot", null, 0, Arrays.asList(new TagDto[]{new TagDto(5L,"sql")})));
        expectedItems.add(new UserDtoList(4L, "Tot", null, 0, Arrays.asList(new TagDto[]{})));
        expectedItems.add(new UserDtoList(5L, "Tot", null, 0, Arrays.asList(new TagDto[]{})));
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
        Assert.assertEquals(expected.toString(), actual.toString());
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
