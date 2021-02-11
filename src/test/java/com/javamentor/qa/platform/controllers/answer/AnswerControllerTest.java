package com.javamentor.qa.platform.controllers.answer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.AbstractIntegrationTest;
import com.javamentor.qa.platform.models.dto.AnswerDto;
import com.javamentor.qa.platform.models.dto.CommentDto;
import com.javamentor.qa.platform.models.dto.CreateAnswerDto;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import com.javamentor.qa.platform.models.entity.question.answer.CommentAnswer;
import com.javamentor.qa.platform.webapp.converters.AnswerConverter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@DataSet(value = {"" +
        "dataset/answer/usersApi.yml",
        "dataset/answer/answerApi.yml",
        "dataset/answer/roleApi.yml",
        "dataset/answer/questionApi.yml",
        "dataset/question/questionQuestionApi.yml",
        "dataset/question/answerQuestionApi.yml"},
        cleanBefore = true, cleanAfter = false)
@WithMockUser(username = "principal@mail.ru", roles={"ADMIN", "USER"})
@ActiveProfiles("local")
class AnswerControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AnswerConverter answerConverter;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void shouldAddCommentToAnswerResponseBadRequestAnswerNotFound() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/question/1/answer/99999/comment")
                .content("This is very good answer!")
                .accept(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8"))
                .andExpect(content().string("Answer not found"));
    }


    @Test
    public void shouldAddCommentToAnswerResponseCommentDto() throws Exception {

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/question/4/answer/14/comment")
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.reputation").value(2))
                .andReturn();

        JSONObject dto = new JSONObject(result.getResponse().getContentAsString());

        List<CommentAnswer> resultList = entityManager.createNativeQuery("select * from comment_answer where comment_id = " + dto.get("id")).getResultList();
        Assert.assertFalse(resultList.isEmpty());
    }


    @Test
    public void shouldGetAllCommentsByAnswer() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/question/9/answer/3/comment")
                .content("This is very good answer!")
                .accept(MediaType.APPLICATION_JSON));

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/question/9/answer/4/comment")
                .content("Hi! I know better than you :-) !")
                .accept(MediaType.APPLICATION_JSON));

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/question/9/answer/3/comment")
                .content("The bad answer!")
                .accept(MediaType.APPLICATION_JSON));

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/question/9/answer/3/comments")
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        JSONArray array = new JSONArray(result.getResponse().getContentAsString());

        Assert.assertEquals(array.length(),2);
    }




    @Test
    void userVotedForAnswerStatusOk() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/question/1/isAnswerVoted"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string("true"));
    }

    @Test
    void userNotVotedForAnswerStatusOk() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/question/2/isAnswerVoted"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string("false"));
    }

    @Test
    void userNotVotedForAnswerCuzQuestionNotFound() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/question/0/isAnswerVoted"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("Question not found"));
    }


    @Test
    @WithMockUser(username = "admin@tut.by", roles = {"ADMIN"})
    public void shouldMarkAnswerAsHelpful() throws Exception {

        Answer beforeAnswer = (Answer) entityManager.createQuery("From Answer Where id=44").getSingleResult();

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                .patch("/api/question/77/answer/44/upVote"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andReturn();

        JSONObject object = new JSONObject(result.getResponse().getContentAsString());

        Answer afterAnswer = (Answer) entityManager.createQuery("From Answer Where id=44").getSingleResult();

        Assert.assertFalse(beforeAnswer.getIsHelpful());
        Assert.assertEquals(object.get("userId"),4);
        Assert.assertTrue(afterAnswer.getIsHelpful());
    }

}