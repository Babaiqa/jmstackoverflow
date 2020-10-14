package com.javamentor.qa.platform.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class CommentDto implements Serializable {
    private Long id;
    private Long userId;
    private String text;
    private LocalDateTime persistDate;
    private LocalDateTime lastRedactionDate;
    private int conmmentType;
}
