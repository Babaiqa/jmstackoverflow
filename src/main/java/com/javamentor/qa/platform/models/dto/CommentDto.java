package com.javamentor.qa.platform.models.dto;

import com.javamentor.qa.platform.models.entity.CommentType;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto implements Serializable {
    private Long id;
    private String text;
    private LocalDateTime persistDate;
    private LocalDateTime lastRedactionDate;
    private CommentType commentType;
    private Long userId;
    private String username;
    private Long reputation;


    public CommentDto(Long id, String text, LocalDateTime persistDate, LocalDateTime lastRedactionDate, CommentType commentType, Long userId, String username) {
        this.id = id;
        this.text = text;
        this.persistDate = persistDate;
        this.lastRedactionDate = lastRedactionDate;
        this.commentType = commentType;
        this.userId = userId;
        this.username = username;
    }
}
