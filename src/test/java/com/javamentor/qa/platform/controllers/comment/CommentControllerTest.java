package com.javamentor.qa.platform.controllers.comment;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.SeedStrategy;
import com.javamentor.qa.platform.AbstractIntegrationTest;
import com.javamentor.qa.platform.models.entity.question.CommentQuestion;
import com.javamentor.qa.platform.models.entity.question.answer.AnswerVote;
import com.javamentor.qa.platform.models.entity.question.answer.CommentAnswer;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

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
@ActiveProfiles("local")
public class CommentControllerTest extends AbstractIntegrationTest {


    @Autowired
    private MockMvc mockMvc;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public  void shouldAddCommentToQuestionStatusOk() throws Exception {
        List<CommentQuestion> before = entityManager.createNativeQuery("select * from comment_question").getResultList();
        int first = before.size();
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/comment/question/1")
                .content("This is very good question!")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        List<CommentQuestion> after = entityManager.createNativeQuery("select * from comment_question").getResultList();
        int second = after.size();
        Assert.assertEquals(first + 1, second);
    }


    @Test
    public void shouldAddCommentToQuestionResponseBadRequestQuestionNotFound() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/comment/question/9999")
                .content("This is very good question!")
                .accept(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8"))
                .andExpect(content().string("Question not found"));
    }

    @Test
    public void shouldAddCommentToQuestionResponseCommentDto() throws Exception {
        List<CommentQuestion> before = entityManager.createNativeQuery("select * from comment_question").getResultList();
        int first = before.size();

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/comment/question/1")
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

        List<CommentQuestion> after = entityManager.createNativeQuery("select * from comment_question").getResultList();
        int second = after.size();
        Assert.assertEquals(first + 1, second);
    }

    @Test
    public  void shouldAddCommentToAnswerStatusOk() throws Exception {
        List<CommentAnswer> before = entityManager.createNativeQuery("select * from comment_answer").getResultList();
        int first = before.size();

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/comment/answer/1")
                .content("This is a good answer")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        List<CommentAnswer> after = entityManager.createNativeQuery("select * from comment_answer").getResultList();
        int second = after.size();
        Assert.assertEquals(first + 1, second);
    }


    @Test
    public void shouldAddCommentToAnswerResponseBadRequestAnswerNotFound() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/comment/answer/9999")
                .content("This is very good answer!")
                .accept(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8"))
                .andExpect(content().string("Answer not found"));
    }

    @Test
    public void shouldAddCommentToAnswerResponseCommentDto() throws Exception {
        List<CommentAnswer> before = entityManager.createNativeQuery("select * from comment_answer").getResultList();
        int first = before.size();

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/comment/answer/1")
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

        List<CommentAnswer> after = entityManager.createNativeQuery("select * from comment_answer").getResultList();
        int second = after.size();
        Assert.assertEquals(first + 1, second);
    }
}

