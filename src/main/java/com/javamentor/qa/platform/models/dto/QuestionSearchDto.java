package com.javamentor.qa.platform.models.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class QuestionSearchDto {

    @NotNull(message = "Поле не должно быть null")
    @NotBlank(message = "Поле не должно быть пустым")
    private String message;
}
