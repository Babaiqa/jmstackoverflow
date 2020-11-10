package com.javamentor.qa.platform.controllers.user;

import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.AbstractIntegrationQuestionControllerTest;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.dto.UserDtoList;
import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
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

public class UserControllerTest extends AbstractIntegrationQuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DataSet(value = "dataset/user/userUserApi.yml", disableConstraints = true, cleanBefore = true, cleanAfter = true)
    public void shouldGetUserByName() throws Exception {
        PageDto<UserDtoList, Object> expected = new PageDto<>();
        expected.setCurrentPageNumber(1);
        expected.setTotalPageCount(1);
        expected.setTotalResultCount(3);
        expected.setItemsOnPage(10);

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
    @DataSet(value = "dataset/user/userApi.yml", disableConstraints = true, cleanBefore = true, cleanAfter = true)
    public void shouldGetUserById() throws Exception {
        this.mockMvc.perform(get("/api/user/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @DataSet(value = "dataset/question/usersQuestionApi.yml", disableConstraints = true, cleanBefore = true, cleanAfter = true)
    public void shouldGetUserByIsNot() throws Exception {
        this.mockMvc.perform(get("/api/user/4"))
                .andDo(print())
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
                .andExpect(status().isBadRequest());
    }
}
