package com.javamentor.qa.platform.controllers.comment;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.SeedStrategy;
import com.javamentor.qa.platform.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataSet(value = {"" +
        "dataset/comment/roleCommentApi.yml",
        "dataset/comment/usersCommentApi.yml",
        "dataset/comment/answerCommentApi.yml",
        "dataset/comment/questionCommentApi.yml",
        "dataset/comment/tagCommentApi.yml",
        "dataset/comment/question_has_tagCommentApi.yml",
        "dataset/comment/votes_on_questionCommentApi.yml"}, cleanBefore = true, cleanAfter = false)
@WithMockUser(username = "principal@mail.ru", roles={"ADMIN", "USER"})
public class CommentControllerTest extends AbstractIntegrationTest {


    @Autowired
    private MockMvc mockMvc;

    @Test
    public  void shouldAddCommentToQuestionStatusOk() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/comment/question/1")
                .param("userId", "1")
                .content("This is very good question!")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    public  void shouldAddCommentToQuestionResponseBadRequestUserNotFound() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/comment/question/1")
                .param("userId", "99999")
                .content("This is very good question!")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User not found"));
    }


    @Test
    public void shouldAddCommentToQuestionResponseBadRequestQuestionNotFound() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/comment/question/9999")
                .param("userId", "1")
                        .content("This is very good question!")
                .accept(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8"))
                .andExpect(content().string("Question not found"));
    }

    @Test
    public void shouldAddCommentToQuestionResponseCommentDto() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/comment/question/1")
                .param("userId", "1")
                .content("This is very good question!")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value("This is very good question!"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.persistDate", org.hamcrest.Matchers.containsString(LocalDate.now().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastRedactionDate", org.hamcrest.Matchers.containsString(LocalDate.now().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.commentType").value("QUESTION"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("Teat"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.reputation").value(2));
    }

    @Test
    public  void shouldAddCommentToAnswerStatusOk() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/comment/answer/{answerId}", 1L)
                .param("userId", "3")
                .content("This is a good answer")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public  void shouldAddCommentToAnswerResponseBadRequestUserNotFound() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/comment/answer/1")
                .param("userId", "99999")
                .content("This is very good answer!")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User not found"));
    }

    @Test
    public void shouldAddCommentToAnswerResponseBadRequestAnswerNotFound() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/comment/answer/9999")
                .param("userId", "1")
                .content("This is very good answer!")
                .accept(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8"))
                .andExpect(content().string("Answer not found"));
    }

    @Test
    public void shouldAddCommentToAnswerResponseCommentDto() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/comment/answer/1")
                .param("userId", "1")
                .content("This is very good answer!")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value("This is very good answer!"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.persistDate", org.hamcrest.Matchers.containsString(LocalDate.now().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastRedactionDate", org.hamcrest.Matchers.containsString(LocalDate.now().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.commentType").value("ANSWER"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("Teat"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.reputation").value(2));
    }
}

