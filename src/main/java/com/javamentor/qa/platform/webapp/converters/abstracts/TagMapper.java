package com.javamentor.qa.platform.webapp.converters.abstracts;

import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.entity.question.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring", uses = Tag.class)
public abstract class TagMapper {
//     public static final TagMapper INSTANCE = Mappers.getMapper( TagMapper.class );
     public abstract List<Tag> dtoToTag (List<TagDto> tagDto);
}
