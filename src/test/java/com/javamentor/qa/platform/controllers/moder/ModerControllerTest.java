package com.javamentor.qa.platform.controllers.moder;

import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.AbstractIntegrationTest;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.webapp.converters.AnswerConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataSet(value = {"" +
        "dataset/moder/roleApiModer.yml",
        "dataset/moder/usersApiModer.yml",
        "dataset/moder/questionApiModer.yml",
        "dataset/moder/answerApiModer.yml"},
        cleanBefore = true)
@ActiveProfiles("local")
public class ModerControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AnswerConverter answerConverter;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @DataSet(value = {"dataset/moder/roleApiModer.yml",
                      "dataset/moder/usersApiModer.yml",
                      "dataset/moder/questionApiModer.yml",
                      "dataset/moder/answerApiModer.yml"},
                      cleanBefore = true, cleanAfter = true)
    @WithMockUser(username = "admin@tut.by", roles = {"MODER"})
    void deleteAnswerFromQuestion_AnswerIsDeletedByModeratorSuccessfully() throws Exception {

        Answer beforeAnswer = (Answer) entityManager.createQuery("from Answer Where id=10").getSingleResult();

        this.mockMvc.perform(MockMvcRequestBuilders
                .patch("/api/moder/10/delete"))
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().string("Answer was deleted by moderator"));

        Answer afterAnswer = (Answer) entityManager.createQuery("from Answer Where id=10").getSingleResult();

        Assertions.assertFalse(beforeAnswer.getIsDeletedByModerator());
        Assertions.assertTrue(afterAnswer.getIsDeletedByModerator());
    }

    @Test
    @DataSet(value = {"dataset/moder/roleApiModer.yml",
            "dataset/moder/usersApiModer.yml",
            "dataset/moder/questionApiModer.yml",
            "dataset/moder/answerApiModer.yml"},
            cleanBefore = true, cleanAfter = true)
    @WithMockUser(username = "admin@tut.by", roles = {"MODER"})
    void deleteAnswerFromQuestion_AnswerIsDeletedByModeratorNotFoundAnswer() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .patch("/api/moder/20/delete"))
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Answer was not found"));

    }

}
