package com.javamentor.qa.platform.dao.util;

import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


//* Определяет отображение между
// Object[] проекцией и PostDTOобъектом, содержащим PostCommentDTOдочерние объекты DTO:*/
@Component
public class QuestionResultTransformer implements ResultTransformer {

    private Map<Long, QuestionDto> questionDtoMap = new LinkedHashMap<>();

    @Override
    public Object transformTuple(Object[] tuple, String[] aliases) {

        Map<String,Integer> aliasToIndexMap= aliasToIndexMap(aliases);
        Long questionId=((Number)tuple[aliasToIndexMap.get(QuestionDto.ID_ALIAS)]).longValue();

        QuestionDto questionDto = questionDtoMap.computeIfAbsent(
                questionId,
                id -> new QuestionDto(tuple, aliasToIndexMap)
        );

        questionDto.getListTagDto().add(
                new TagDto(tuple,aliasToIndexMap)
        );

        return questionDto;
    }


    @Override
    public List transformList(List list) {
        return new ArrayList<>(questionDtoMap.values());
    }


    public  Map<String, Integer> aliasToIndexMap(
            String[] aliases) {

        Map<String, Integer> aliasToIndexMap = new LinkedHashMap<>();

        for (int i = 0; i < aliases.length; i++) {
            aliasToIndexMap.put(aliases[i], i);
        }

        return aliasToIndexMap;
    }
}
