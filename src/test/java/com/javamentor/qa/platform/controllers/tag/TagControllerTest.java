package com.javamentor.qa.platform.controllers.tag;

import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@DataSet(value = {
        "dataset/question/tagQuestionApi.yml"}
        , cleanBefore = true, cleanAfter = true)
public class TagControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void correctRequestGetTagDtoPaginationByPopular() throws Exception {
        mockMvc.perform(get("/api/tag/popular")
                .param("page", "1")
                .param("size", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.items[0].id").value(1))
                .andExpect(jsonPath("$.items[0].name").value("java"));
    }

    @Test
    public void uncorrectRequestGetTagDtoPaginationByPopular() throws Exception {
        mockMvc.perform(get("/api/tag/popular")
                .param("page", "0")
                .param("size", "10"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}
