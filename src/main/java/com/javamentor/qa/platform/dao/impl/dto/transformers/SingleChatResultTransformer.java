package com.javamentor.qa.platform.dao.impl.dto.transformers;

import com.javamentor.qa.platform.models.dto.SingleChatDto;
import org.hibernate.transform.ResultTransformer;

import java.sql.Timestamp;
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
                    singleChatDtoTemp.setTitle((String)tuple[aliasToIndexMapTrans.get("title")]);
                    singleChatDtoTemp.setUserSenderId(((Number)tuple[aliasToIndexMapTrans.get("user_sender_id")]).longValue());
                    singleChatDtoTemp.setUserRecipientId(((Number)tuple[aliasToIndexMapTrans.get("user_recipient_id")]).longValue());
                    singleChatDtoTemp.setNickname((String)tuple[aliasToIndexMapTrans.get("full_name")]);
                    singleChatDtoTemp.setImageLink((String)tuple[aliasToIndexMapTrans.get("image_link")]);
                    singleChatDtoTemp.setMessage((String)tuple[aliasToIndexMapTrans.get("message")]);
                    singleChatDtoTemp.setUserSenderIdCheck(((Number)tuple[aliasToIndexMapTrans.get("user_sender_id_check_id")]).longValue());
                    singleChatDtoTemp.setLastRedactionDate(((Timestamp)tuple[aliasToIndexMapTrans.get("last_redaction_date")]));
                    return singleChatDtoTemp;
                });
    }

    @Override
    public List transformList(List list) {
        return new ArrayList<>(singleChatDtoMap.values());
    }
}
