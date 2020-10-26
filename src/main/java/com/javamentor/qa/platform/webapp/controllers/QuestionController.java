package com.javamentor.qa.platform.webapp.controllers;

import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.service.abstracts.dto.QuestionDtoService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;

import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.TagService;
import com.javamentor.qa.platform.webapp.converters.abstracts.TagMapper;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("/api/question/")
@Api(value = "QuestionApi")
public class QuestionController {
    private QuestionService questionService;

    private final QuestionService questionService;
    private final TagMapper tagMapper;
    private final TagService tagService;
    private final QuestionDtoService questionDtoService;

    @Autowired
    public QuestionController(QuestionService questionService, QuestionDtoService questionDtoService) {
        this.questionService = questionService;
    public QuestionController(QuestionService questionService, TagMapper tagMapper, TagService tagService,
                              QuestionDtoService questionDtoService) {
        this.questionService = questionService;
        this.tagMapper = tagMapper;
        this.tagService = tagService;
        this.questionDtoService = questionDtoService;
    }

    @DeleteMapping("/{id}/delete")
    @ApiOperation(value = "Delete question", response = String.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Deletes the question.", response = String.class),
            @ApiResponse(code = 400, message = "Wrong ID", response = String.class)
    })
    public ResponseEntity<String> deleteQuestionById(@ApiParam(name = "id") @PathVariable Long id) {
        if (Boolean.TRUE.equals(questionService.existsById(id))) {
            questionService.delete(questionService.getById(id).get());
            return ResponseEntity.ok("Question was deleted");
        } else {
            return ResponseEntity.badRequest().body("Wrong ID");
        }
    }

    @PatchMapping("{QuestionId}/tag/add")
    @ResponseBody
    @ApiResponses({
            @ApiResponse(code = 200, message = "Tags were added", response = String.class),
            @ApiResponse(code = 400, message = "Question not found",response = String.class)
    })
    public ResponseEntity<?> setTagForQuestion(
            //@RequestBody List<TagDto> tagDto, @PathVariable Long QuestionId )
            @ApiParam(name = "QuestionId", value = "type Long", required = true, example = "0")
            @PathVariable Long QuestionId,
            @ApiParam(name = "tagDto", value = "type List<TagDto>", required = true)
            @RequestBody List<TagDto> tagDto)
            {

        if (QuestionId == null) {
            return ResponseEntity.badRequest().body("Question id is null");
        }
        List<Tag> listTag = tagMapper.dtoToTag(tagDto); // Список тегов полученных в контроллере (от фронта)

        Optional<Question> question = questionService.getById(QuestionId);
        if (!question.isPresent()){
            return ResponseEntity.badRequest().body("Question not found");
        }
        List<Tag> listTagQuestion = Collections.emptyList();
        for (Tag tag : listTag) {
            Optional <Tag> tagFromDB = tagService.getTagByName(tag.getName());
            if (!tagFromDB.isPresent()) {
                tag.setDescription("new "+ tag.getName());
                tagService.persist(tag);
            }
            else {tag = tagFromDB.get();}

            listTagQuestion = question.get().getTags();
            if (!listTagQuestion.contains(tag)) listTagQuestion.add(tag);
            question.get().setTags(listTagQuestion);
        }
        return  ResponseEntity.ok().body("Tags were added");
    }



    @GetMapping("{id}")
    @ApiOperation(value = "get QuestionDto", response = String.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returns the QuestionDto", response = QuestionDto.class),
            @ApiResponse(code = 400, message = "Question not found", response = String.class)
    })

    public ResponseEntity<?> getQuestionById(
            @ApiParam(name = "id", value = "type Long", required = true, example = "0")
            @PathVariable Long id) {

        Optional<QuestionDto> questionDto = questionDtoService.getQuestionDtoById(id);

        return questionDto.isPresent() ? ResponseEntity.ok(questionDto.get()) :
                ResponseEntity.badRequest().body("Question not found");
    }
}




