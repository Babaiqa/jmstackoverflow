package com.javamentor.qa.platform.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class UserProfileDto implements Serializable {
    private static final long serialVersionUID = -2073789687153413140L;
    private Long id;
    private String fullName;
    private String password;
    private LocalDateTime persistDate;
    private Long roleId;
    private LocalDateTime lastRedactionDate;
    private String email;
    private String about;
    private String city;
    private String linkSite;
    private String linkGithub;
    private String linkVk;
    private int reputationCount;
    private Boolean isEnable;
    private byte[] image;
}
