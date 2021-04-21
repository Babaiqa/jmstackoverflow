package com.javamentor.qa.platform.controllers.moder;

import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.AbstractIntegrationTest;
import com.javamentor.qa.platform.models.dto.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DataSet(value = {"dataset/question/roleQuestionApi.yml",
        "dataset/question/usersQuestionApi.yml",
        "dataset/question/answerQuestionApi.yml",
        "dataset/question/questionQuestionApi.yml",
        "dataset/question/tagQuestionApi.yml",
        "dataset/question/question_has_tagQuestionApi.yml",
        "dataset/question/votes_on_question.yml",
        "dataset/comment/comment.yml",
        "dataset/comment/comment_question.yml",
        "dataset/question/question_viewed.yml",},
        useSequenceFiltering = true, cleanBefore = true, cleanAfter = false)
@WithMockUser(username = "principal@mail.ru", roles = {"MODER"})
@ActiveProfiles("local")
public class ModerControllerTest extends AbstractIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getQuestionDto() throws Exception {
        String resultContext = this.mockMvc.perform(get("/api/moder/question/13"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        QuestionDto questionDto = objectMapper.readValue(resultContext, QuestionDto.class);

        Assertions.assertEquals(0,questionDto.getCountValuable());
    }

    @Test
    public void shouldUpdateQuestionStatusOk() throws Exception {
        QuestionCreateDto questionCreateDto = new QuestionCreateDto();
        questionCreateDto.setUserId(1L);
        questionCreateDto.setTitle("Moder changed the title");
        questionCreateDto.setDescription("Moder changed the description");
        List<TagDto> listTagsAdd = new ArrayList<>();
        listTagsAdd.add(new TagDto(5L, "Structured Query Language", "description"));
        questionCreateDto.setTags(listTagsAdd);

        String jsonRequest = objectMapper.writeValueAsString(questionCreateDto);

        ResultActions resultActions = this.mockMvc.perform(put("/api/moder/question/13")
                .contentType("application/json;charset=UTF-8")
                .content(jsonRequest))
                .andExpect(status().isOk());

        resultActions
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Moder changed the title"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Moder changed the description"));

    }
}
