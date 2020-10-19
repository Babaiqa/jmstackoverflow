package com.javamentor.qa.platform.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

import static ognl.OgnlOps.stringValue;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TagDto implements Serializable {

    public static final String ID_ALIAS = "t_id";
    public static final String NAME_ALIAS = "t_name";

    private static final long serialVersionUID = -8087563918115872879L;
    private Long id;

    private String name;

    public TagDto(
            Object[] tuples,
            Map<String, Integer> aliasToIndexMap) {
        this.id = ((Number) tuples[aliasToIndexMap.get(ID_ALIAS)]).longValue();
        this.name = tuples[aliasToIndexMap.get(NAME_ALIAS)].toString();
    }

}
