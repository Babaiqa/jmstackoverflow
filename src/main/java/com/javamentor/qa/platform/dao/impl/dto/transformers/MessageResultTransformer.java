package com.javamentor.qa.platform.dao.impl.dto.transformers;

import com.javamentor.qa.platform.models.dto.MessageDto;
import org.hibernate.transform.ResultTransformer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// для потомков , эта собака сутулая не работает  хз почему , мож разберется кто=)
public class MessageResultTransformer implements ResultTransformer {

    private Map<Long, MessageDto> messageDtoMap = new LinkedHashMap<>();

    @Override
    public Object transformTuple(Object[] tuple, String[] aliases) {
        Map<String, Integer> aliasToIndexMapTrans = AliasToIndexMap.get(aliases);
        Long messageId = ((Number) tuple[0]).longValue();

        return messageDtoMap.computeIfAbsent(
                messageId,
                id -> {
                    MessageDto messageDtoTemp = new MessageDto();
                    messageDtoTemp.setId(((Number) tuple[aliasToIndexMapTrans.get("id")]).longValue());
                    messageDtoTemp.setMessage((String) tuple[aliasToIndexMapTrans.get("message")]);
                    messageDtoTemp.setLastRedactionDate(((LocalDateTime) tuple[aliasToIndexMapTrans.get("last_redaction_date")]));
                    messageDtoTemp.setPersistDate(((LocalDateTime) tuple[aliasToIndexMapTrans.get("persist_date")]));
                    messageDtoTemp.setChatId(((Number) tuple[aliasToIndexMapTrans.get("chat_id")]).longValue());
                    messageDtoTemp.setUserSenderId(((Number) tuple[aliasToIndexMapTrans.get("user_sender_id")]).longValue());

                    return messageDtoTemp;
                });

    }

    @Override
    public List transformList(List list) {
        return new ArrayList<>(messageDtoMap.values());
    }
}
