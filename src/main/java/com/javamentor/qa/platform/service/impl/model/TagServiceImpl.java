package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.TagDao;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl extends ReadWriteServiceImpl<Tag, Long> implements TagService {

    @Autowired
    TagDao tagDao;

    @Autowired
    QuestionService questionService;

    public TagServiceImpl(TagDao tagDao) {
        super(tagDao);
    }

    @Override
    public Optional<Tag> getTagByName(String name) {
        return tagDao.getTagByName(name);
    }

    @Transactional
    @Override
    public void addTagToQuestion(List<Tag> listOfTags, Question question) {
        for(Tag tag : listOfTags){
            if(!getTagByName(tag.getName()).isPresent()) {
                tag.setDescription(tag.getName());
                tagDao.persist(tag);
            }
            else {
                tag = getTagByName(tag.getName()).get();
            }
            List<Tag> listTagQuestion= questionService.getAllTagOfQuestion(question);
            if(!listTagQuestion.contains(tag)) {
                listTagQuestion.add(tag);
            }
            question.setTags(listTagQuestion);
        }
    }
}
