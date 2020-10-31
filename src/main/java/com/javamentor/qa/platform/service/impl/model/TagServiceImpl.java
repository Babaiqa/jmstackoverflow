package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.QuestionDao;
import com.javamentor.qa.platform.dao.abstracts.model.TagDao;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.service.abstracts.model.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl extends ReadWriteServiceImpl<Tag, Long> implements TagService {

    @Autowired
    private TagDao tagDao;

    final private QuestionDao questionDao;

    public TagServiceImpl(TagDao tagDao, QuestionDao questionDao) {
        super(tagDao);
        this.questionDao = questionDao;
    }

    @Override
    public Optional<Tag> getTagByName(String name) {
        return tagDao.getTagByName(name);
    }

    @Transactional
    @Override
    public void addTagToQuestion(List<Tag> listOfTags, Question question) {
        for(Tag tag : listOfTags){
            if(tag.getId()!=null) {
                tag= Tag.builder().name(tag.getName()).description(tag.getDescription()).build();

            }
            if(tag.getName()==null) {
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE ,
                        "поле тэга name не может быть  null", new Exception("name can't be null"));
            }

            Optional<Tag> tagOptional = getTagByName(tag.getName());

            if(!tagOptional.isPresent()) {
                tag.setDescription(tag.getName());
                tagDao.persist(tag);
            }
            else {
                tag = tagOptional.get();
            }

            List<Tag> listTagQuestion= questionDao.getAllTagOfQuestion(question);
            if(!listTagQuestion.contains(tag)) {
                listTagQuestion.add(tag);
            }
            question.setTags(listTagQuestion);
        }

    }
}
