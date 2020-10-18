package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.impl.model.RelatedTagDaoImpl;
import com.javamentor.qa.platform.models.entity.question.RelatedTag;
import org.springframework.stereotype.Service;

@Service
public class RelatedTagService extends ReadWriteServiceImpl<RelatedTag, Long> {
    public RelatedTagService(RelatedTagDaoImpl relatedTagDaoImpl) {
        super(relatedTagDaoImpl);
    }
}
