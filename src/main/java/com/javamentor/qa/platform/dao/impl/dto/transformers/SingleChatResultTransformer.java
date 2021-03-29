package com.javamentor.qa.platform.dao.impl.dto.transformers;

import com.javamentor.qa.platform.models.dto.SingleChatDto;
import org.hibernate.transform.ResultTransformer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SingleChatResultTransformer implements ResultTransformer {

    private Map<Long, SingleChatDto> singleChatDtoMap = new LinkedHashMap<>();

    @Override
    public Object transformTuple(Object[] tuple, String[] aliases) {

        Map<String, Integer> aliasToIndexMapTrans = AliasToIndexMap.get(aliases);
        Long singleChatId = ((Number) tuple[0]).longValue();

        return singleChatDtoMap.computeIfAbsent(
                singleChatId,
                id -> {
                    SingleChatDto singleChatDtoTemp = new SingleChatDto();
                    singleChatDtoTemp.setId(((Number)tuple[aliasToIndexMapTrans.get("id")]).longValue());
                    singleChatDtoTemp.setChatId(((Number)tuple[aliasToIndexMapTrans.get("chatId")]).longValue());
                    singleChatDtoTemp.setTitle((String)tuple[aliasToIndexMapTrans.get("title")]);
                    singleChatDtoTemp.setUserOneId(((Number)tuple[aliasToIndexMapTrans.get("userOneId")]).longValue());
                    singleChatDtoTemp.setUserTwoId(((Number)tuple[aliasToIndexMapTrans.get("userTwoId")]).longValue());

                    return singleChatDtoTemp;
                });
    }

    @Override
    public List transformList(List list) {
        return new ArrayList<>(singleChatDtoMap.values());
    }
}
