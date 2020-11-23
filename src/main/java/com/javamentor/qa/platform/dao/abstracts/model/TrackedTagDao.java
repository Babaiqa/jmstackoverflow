package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.TrackedTag;

import java.util.List;


public interface TrackedTagDao extends ReadWriteDao<TrackedTag, Long>{
    List<TrackedTag> getTrackedTagsByUser(String name);

    void addTrackedTag(TrackedTag trackedTag);

}
