package com.javamentor.qa.platform.models.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TagRecentDto implements Serializable {
    private Long id;
    private String name;
    private int countTagToQuestion;
}
