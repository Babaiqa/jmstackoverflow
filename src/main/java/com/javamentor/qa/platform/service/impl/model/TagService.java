package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.impl.model.TagDaoImpl;
import com.javamentor.qa.platform.models.entity.question.Tag;
import org.springframework.stereotype.Service;

@Service
public class TagService extends ReadWriteServiceImpl<Tag, Long> {
    public TagService(TagDaoImpl tagDaoImpl) {
        super(tagDaoImpl);
    }
}
