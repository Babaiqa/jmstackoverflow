package com.javamentor.qa.platform.models.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserPublicInfoDto implements Serializable {

    @NotNull
    private String nickname;

    private String about;
    private String linkImage;
    private String linkSite;
    private String linkVk;
    private String linkGitHub;

    @NotNull
    private String fullName;

    private String city;
}

