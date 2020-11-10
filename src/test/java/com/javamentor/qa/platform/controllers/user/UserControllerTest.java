package com.javamentor.qa.platform.controllers.user;

import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.AbstractIntegrationTest;
import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
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
}
