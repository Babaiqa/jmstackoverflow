package com.javamentor.qa.platform.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TagListDto {
    long id;
    String name;
    String description;
    long countQuestion;
    long countQuestionToWeek;
    long countQuestionToDay;

    @Override
    public String toString() {
        return "TagListDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", countQuestion=" + countQuestion +
                ", countQuestionToWeek=" + countQuestionToWeek +
                ", countQuestionToDay=" + countQuestionToDay +
                '}';
    }
}
