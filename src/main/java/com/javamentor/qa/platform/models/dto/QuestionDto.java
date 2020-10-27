package com.javamentor.qa.platform.models.dto;

import lombok.*;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto implements Serializable {
    private static final long serialVersionUID = -3497942278821733961L;

    @Null
    private Long id;
    @NotNull
    private String title;
    @NotNull
    private Long authorId;
    @NotNull
    private String authorName;
    @NotNull
    private String authorImage;
    @NotNull
    private String description;
    @NotNull
    private int viewCount;
    @NotNull
    private int countAnswer;
    @NotNull
    private int countValuable;

    private LocalDateTime persistDateTime;

    private LocalDateTime lastUpdateDateTime;

    private List<TagDto> listTagDto;

}


