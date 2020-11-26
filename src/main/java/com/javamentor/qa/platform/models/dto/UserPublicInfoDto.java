package com.javamentor.qa.platform.models.dto;

import com.javamentor.qa.platform.models.util.OnUpdate;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserPublicInfoDto implements Serializable {

    @NotNull (groups = OnUpdate.class, message = "Поле не должно быть null")
    @NotBlank (groups = OnUpdate.class, message = "Поле не должно быть пустым")
    private String nickname;

    private String about;
    private String linkImage;
    private String linkSite;
    private String linkVk;
    private String linkGitHub;

    @NotNull (groups = OnUpdate.class, message = "Поле не должно быть null")
    @NotBlank (groups = OnUpdate.class, message = "Поле не должно быть пустым")
    private String fullName;

    private String city;
}

