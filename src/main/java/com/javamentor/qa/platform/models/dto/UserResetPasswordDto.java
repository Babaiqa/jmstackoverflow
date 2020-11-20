package com.javamentor.qa.platform.models.dto;

import com.javamentor.qa.platform.models.util.OnUpdate;
import lombok.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserResetPasswordDto {

    @NotBlank(groups = OnUpdate.class, message = "Поле не должно быть пустым")
    private String oldPassword;

    @NotBlank(groups = OnUpdate.class, message = "Поле не должно быть пустым")
    private String newPassword;
}
