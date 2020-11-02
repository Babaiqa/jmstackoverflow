package com.javamentor.qa.platform.controllers.tag;

import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.AbstractIntegrationQuestionControllerTest;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.TagListDto;
import com.javamentor.qa.platform.webapp.controllers.TagController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;



@DataSet(value = {"dataset/question/roleQuestionApi.yml",
        "dataset/question/questionQuestionApi.yml",
        "dataset/question/usersQuestionApi.yml",
        "dataset/question/tagQuestionApi.yml",
        "dataset/question/question_has_tagQuestionApi.yml"}
        , cleanBefore = true, cleanAfter = true)
public class TagControllerTest   extends AbstractIntegrationQuestionControllerTest {

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
    void theNumberOfQuestionsIsCorrect() throws Exception {
        PageDto<TagListDto, Object> page = (PageDto<TagListDto, Object>) tagController.getTagDtoPaginationOrderByAlphabet(1,5).getBody();
        assertThat(page.getItems().get(0).getCountQuestion()).isEqualTo(6);
        assertThat(page.getItems().get(1).getCountQuestion()).isEqualTo(1);
        assertThat(page.getItems().get(2).getCountQuestion()).isEqualTo(2);
        assertThat(page.getItems().get(3).getCountQuestion()).isEqualTo(0);
        assertThat(page.getItems().get(4).getCountQuestion()).isEqualTo(0);
    }

    @Test
    @Transactional
    void theNumberOfQuestionsForWeekIsCorrect() throws Exception {
        PageDto<TagListDto, Object> page = (PageDto<TagListDto, Object>) tagController.getTagDtoPaginationOrderByAlphabet(1,5).getBody();
        assertThat(page.getItems().get(0).getCountQuestionToWeek()).isEqualTo(3);
        assertThat(page.getItems().get(1).getCountQuestionToWeek()).isEqualTo(0);
        assertThat(page.getItems().get(2).getCountQuestionToWeek()).isEqualTo(1);
        assertThat(page.getItems().get(3).getCountQuestionToWeek()).isEqualTo(0);
        assertThat(page.getItems().get(4).getCountQuestionToWeek()).isEqualTo(0);
    }

    @Test
    @Transactional
    void theNumberOfQuestionsForDayIsCorrect() throws Exception {
        PageDto<TagListDto, Object> page = (PageDto<TagListDto, Object>) tagController.getTagDtoPaginationOrderByAlphabet(1,5).getBody();
        assertThat(page.getItems().get(0).getCountQuestionToDay()).isEqualTo(0);
        assertThat(page.getItems().get(1).getCountQuestionToDay()).isEqualTo(0);
        assertThat(page.getItems().get(2).getCountQuestionToDay()).isEqualTo(0);
        assertThat(page.getItems().get(3).getCountQuestionToDay()).isEqualTo(0);
        assertThat(page.getItems().get(4).getCountQuestionToDay()).isEqualTo(0);
    }

    @Test
    @Transactional
    void IsListOfItemsInAlphabetOrder() throws Exception {
        PageDto<TagListDto, Object> page = (PageDto<TagListDto, Object>) tagController.getTagDtoPaginationOrderByAlphabet(1,5).getBody();
        Comparator<TagListDto> comparator = Comparator.comparing(obj -> obj.getName());
        assertThat(page.getItems()).isSortedAccordingTo(comparator);
    }
}
