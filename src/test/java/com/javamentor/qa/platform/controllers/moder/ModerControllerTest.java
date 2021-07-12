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
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
    private static final String DELETE_Q10_T1 = "/api/moder/1/10/delete";
    private static final String DELETE_Q10_T10 = "/api/moder/10/10/delete";

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

    @DataSet(value = {"dataset/question/roleQuestionApi.yml",
            "dataset/question/usersQuestionApi.yml",
            "dataset/question/questionQuestionApi.yml",
            "dataset/question/tagQuestionApi.yml",
            "dataset/question/question_has_tagQuestionApi.yml",
            "dataset/tag/tracked_tag.yml",
            "dataset/tag/ignored_tag.yml"}
            , cleanBefore = true, cleanAfter = true)
    @Test
    @WithMockUser(username = "principal@mail.ru", roles = {"MODER"})
    public void deleteTagFromQuestionStatusOk() throws Exception {

        mockMvc.perform(delete(DELETE_Q10_T1))
                .andExpect(status().isOk())
                .andExpect(content().string("The tag was removed by a moderator"));

        Long tagForCheckId = null;

        try{
            Query query = entityManager.createNativeQuery("select tag_id from question_has_tag where question_id = 10 and tag_id = 1;");
            tagForCheckId = (Long) query.getSingleResult();
        } catch (NoResultException exception) {
            System.out.println(exception.getMessage());
        }
        Assertions.assertNull(tagForCheckId);

    }

    @DataSet(value = {"dataset/question/roleQuestionApi.yml",
            "dataset/question/usersQuestionApi.yml",
            "dataset/question/questionQuestionApi.yml",
            "dataset/question/tagQuestionApi.yml",
            "dataset/question/question_has_tagQuestionApi.yml",
            "dataset/tag/tracked_tag.yml",
            "dataset/tag/ignored_tag.yml"}
            , cleanBefore = true, cleanAfter = true)
    @Test
    @WithMockUser(username = "principal@mail.ru", roles = {"MODER"})
    public void deleteTagFromQuestionStatusBadRequest() throws Exception {

        mockMvc.perform(delete(DELETE_Q10_T10))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Tag was not found"));

    }

}
