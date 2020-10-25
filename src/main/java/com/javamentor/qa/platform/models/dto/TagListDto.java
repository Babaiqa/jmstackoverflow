package com.javamentor.qa.platform.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TagListDto implements Serializable {
    private Long id;
    private String name;
    private String description;
    private int countQuestion;
    private int countQuestionToWeek;
    private int countQuestionToDay;

    public TagListDto(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
