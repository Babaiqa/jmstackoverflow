package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.IgnoredTagDao;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.models.entity.question.IgnoredTag;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.IgnoredTagService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IgnoredTagServiceImpl extends ReadWriteServiceImpl<IgnoredTag, Long> implements IgnoredTagService {

    private final IgnoredTagDao ignoredTagDao;

    public IgnoredTagServiceImpl(IgnoredTagDao ignoredTagDao){
        super(ignoredTagDao);
        this.ignoredTagDao = ignoredTagDao;
    }

    @Override
    public List<IgnoredTag> getIgnoredTagsByUser(User user) {
        return ignoredTagDao.getIgnoredTagsByUser(user.getUsername());
    }

    @Override
    public List<User> getUsersByIgnoredTag(Tag tag) {
        return null;
    }

    @Override
    public void addIgnoredTagsToUser(List<Tag> tagList, User user) {
        for (Tag tag: tagList) {
            IgnoredTag ignoredTag = new IgnoredTag();
            ignoredTag.setIgnoredTag(tag);
            ignoredTag.setUser(user);
            ignoredTagDao.addIgnoredTag(ignoredTag);
        }

    }

    @Override
    public List<TagDto> getIgnoredTagsByPrincipal(Optional<UserDto> principal) {
        List<IgnoredTag> ignoredTagList = ignoredTagDao.getIgnoredTagsByPrincipal(principal.get().getId());
        List<TagDto> tagList = new ArrayList<>();
        ignoredTagList
                .stream()
                .map(IgnoredTag::getIgnoredTag)
                .collect(Collectors.toList())
                .forEach(tag -> tagList.add(new TagDto(tag.getId(), tag.getName())));
        return tagList;
    }

}
