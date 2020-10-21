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
    int countQuestion;
    int countQuestionToWeek;
    int countQuestionToDay;
}
