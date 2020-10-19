package com.javamentor.qa.platform.models.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto implements Serializable {
    private static final long serialVersionUID = -3497942278821733961L;

    public static final String ID_ALIAS = "q_id";
    public static final String TITLE_ALIAS = "q_title";

    public static final String AUTHOR_ID_ALIAS = "q_authorId";
    public static final String AUTHOR_NAME_ALIAS = "q_authorName";
    public static final String AUTHOR_IMAGE_ALIAS = "q_authorImage";

    public static final String DESCRIPTION_ALIAS = "q_description";

    public static final String VIEW_COUNT_ALIAS = "q_viewCount";
    public static final String COUNT_ANSWER_ALIAS = "q_countAnswer";
    public static final String COUNT_VALUABLE_ALIAS = "q_countValuable";


    public static final String LAST_UPDATE_DATE_TIME_ALIAS = "q_lastUpdateDateTime";
    public static final String PERSIST_DATE_TIME_ALIAS = "q_persistDateTime";

    public static final String IS_HELP_FULL_ALIAS = "q_isHelpful";


    private Long id;
    private String title;

    private Long authorId;
    private String authorName;
    private String authorImage;

    private String description;

    private int viewCount;
    private int countAnswer;
    private int countValuable;

    private LocalDateTime persistDateTime;
    private LocalDateTime lastUpdateDateTime;

    private Boolean isHelpful;

    private List<TagDto> listTagDto = new ArrayList<>();

    public QuestionDto(
            Object[] tuples,
            Map<String, Integer> aliasToIndexMap) {

        this.id = ((Number) tuples[aliasToIndexMap.get(ID_ALIAS)]).longValue();
        this.title = tuples[aliasToIndexMap.get(TITLE_ALIAS)].toString();

        this.authorId = ((Number) tuples[aliasToIndexMap.get(AUTHOR_ID_ALIAS)]).longValue();
        this.authorName = tuples[aliasToIndexMap.get(AUTHOR_NAME_ALIAS)].toString();
        this.authorImage = tuples[aliasToIndexMap.get(AUTHOR_IMAGE_ALIAS)].toString();

        this.description = tuples[aliasToIndexMap.get(DESCRIPTION_ALIAS)].toString();

        this.viewCount = ((Number) tuples[aliasToIndexMap.get(VIEW_COUNT_ALIAS)]).intValue();
        this.countAnswer = ((Number) tuples[aliasToIndexMap.get(COUNT_ANSWER_ALIAS)]).intValue();
        this.countValuable = ((Number) tuples[aliasToIndexMap.get(COUNT_VALUABLE_ALIAS)]).intValue();

        this.persistDateTime = ((LocalDateTime) tuples[aliasToIndexMap.get(PERSIST_DATE_TIME_ALIAS)]);
        this.lastUpdateDateTime = ((LocalDateTime) tuples[aliasToIndexMap.get(LAST_UPDATE_DATE_TIME_ALIAS)]);

        this.isHelpful = (Boolean) tuples[aliasToIndexMap.get(IS_HELP_FULL_ALIAS)];
    }


}


