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
}
