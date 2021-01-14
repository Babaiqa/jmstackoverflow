package com.javamentor.qa.platform.models.dto;

import com.javamentor.qa.platform.models.util.OnCreate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAnswerDto implements Serializable {

    @NotNull(groups = OnCreate.class, message = "Значение body отсутствует")
    @NotBlank(groups = OnCreate.class, message = "Значение body не должно быть пустым")
    private String body;

    @NotNull(groups = OnCreate.class, message = "Значение userId должно быть заполнено")
    private Long userId;

    @NotNull(groups = OnCreate.class, message = "Значение questionId должно быть заполнено")
    private Long questionId;

}
