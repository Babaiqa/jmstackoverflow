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
    private long id;
    private String name;
    private String description;
    private long countQuestion;
    private long countQuestionToWeek;
    private long countQuestionToDay;

    public TagListDto(Long id, String name, String description, long countQuestion, long countQuestionToWeek) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.countQuestion = countQuestion;
        this.countQuestionToWeek = countQuestionToWeek;
    }


    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name=" + name +
                ", description=" + description +
                ", countQuestion=" + countQuestion +
                ", countQuestionToWeek=" + countQuestionToWeek +
                ", countQuestionToDay=" + countQuestionToDay +
                '}';
    }

}
