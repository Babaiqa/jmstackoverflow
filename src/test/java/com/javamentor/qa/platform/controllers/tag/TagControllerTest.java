package com.javamentor.qa.platform.controllers.tag;

import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.AbstractIntegrationTest;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.TagListDto;
import com.javamentor.qa.platform.webapp.controllers.TagController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;



@DataSet(value = {
        "dataset/question/tagQuestionApi.yml"}
        , cleanBefore = true, cleanAfter = true)
public class TagControllerTest   extends AbstractIntegrationTest {

    @Autowired
    private TagController tagController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(tagController).isNotNull();
    }

    @Test
    @Transactional
    void statusIsSuccessful() throws Exception {
        assertThat(tagController.getTagDtoPaginationOrderByAlphabet(1,1).getStatusCode().is2xxSuccessful());
    }

    @Test
    @Transactional
    void fullTagListSizeIsCorrect() throws Exception {
        PageDto<TagListDto, Object> page = (PageDto<TagListDto, Object>) tagController.getTagDtoPaginationOrderByAlphabet(1,100).getBody();
        assertThat(page.getItems().size()).isEqualTo(5);
    }

    @Test
    @Transactional
    void paginationIsCorrectWhenTheNumberOfElementsIsNotAMultipleOfTheNumberOfPages() throws Exception {
        PageDto<TagListDto, Object> page = (PageDto<TagListDto, Object>) tagController.getTagDtoPaginationOrderByAlphabet(1,2).getBody();
        assertThat(page.getTotalPageCount()).isEqualTo(3);
    }

    @Test
    @Transactional
    void theNumberOfQuestionsIsNotNull() throws Exception {
        PageDto<TagListDto, Object> page = (PageDto<TagListDto, Object>) tagController.getTagDtoPaginationOrderByAlphabet(1,5).getBody();
        page.getItems().stream().forEach(q->assertThat(q.getCountQuestion()).isNotNull());
    }

    @Test
    @Transactional
    void theNumberOfQuestionsForWeekIsNotNull() throws Exception {
        PageDto<TagListDto, Object> page = (PageDto<TagListDto, Object>) tagController.getTagDtoPaginationOrderByAlphabet(1,5).getBody();
        page.getItems().stream().forEach(q->assertThat(q.getCountQuestionToWeek()).isNotNull());
    }

    @Test
    @Transactional
    void theNumberOfQuestionsForDayIsNotNull() throws Exception {
        PageDto<TagListDto, Object> page = (PageDto<TagListDto, Object>) tagController.getTagDtoPaginationOrderByAlphabet(1,5).getBody();
        page.getItems().stream().forEach(q->assertThat(q.getCountQuestionToDay()).isNotNull());
    }

    @Test
    @Transactional
    void IsListOfItemsInAlphabetOrder() throws Exception {
        PageDto<TagListDto, Object> page = (PageDto<TagListDto, Object>) tagController.getTagDtoPaginationOrderByAlphabet(1,5).getBody();
        Comparator<TagListDto> comparator = Comparator.comparing(obj -> obj.getName());
        assertThat(page.getItems()).isSortedAccordingTo(comparator);
    }
}
