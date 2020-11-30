package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.IgnoredTag;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.models.entity.user.User;

import java.util.*;


public interface IgnoredTagDao extends ReadWriteDao<IgnoredTag, Long>{
    List<IgnoredTag> getIgnoredTagsByUser(String name);

    void addIgnoredTag(IgnoredTag ignoredTag);

}
