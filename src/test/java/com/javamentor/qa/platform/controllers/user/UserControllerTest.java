package com.javamentor.qa.platform.controllers.user;

import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.AbstractIntegrationQuestionControllerTest;
import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends AbstractIntegrationQuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
