package com.javamentor.qa.platform.controllers.Authentication;

import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.AbstractIntegrationTest;
import com.javamentor.qa.platform.security.dto.UserAuthorizationDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataSet(value = {"dataset/question/roleQuestionApi.yml",
                  "dataset/question/usersQuestionApi.yml"})
class AuthenticationControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

//    @Test
//    void getToken() throws Exception{
//        UserAuthorizationDto user = new UserAuthorizationDto("Test1@mail.ru", "Test1@mail.ru");
//
//        String jsonReqest = objectMapper.writeValueAsString(user);
//
//        this.mockMvc.perform(MockMvcRequestBuilders
//                .post("/api/auth/token")
//                .contentType("application/json;charset=UTF-8")
//                .content(jsonReqest))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void getPrincipalUser() {
//    }
//
//    @Test
//    void auntheticatedCheck() {
//    }
}