package com.javamentor.qa.platform.models.dto;

import com.javamentor.qa.platform.models.util.OnCreate;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserResetPasswordDto {

    private Long id;

    @NotNull(groups = OnCreate.class, message = "Значение отсутствует")
    private String oldPassword;

    @NotNull(groups = OnCreate.class, message = "Значение отсутствует")
    private String newPassword;

    @NotNull(groups = OnCreate.class, message = "Значение отсутствует")
    private String reNewPassword;
}
