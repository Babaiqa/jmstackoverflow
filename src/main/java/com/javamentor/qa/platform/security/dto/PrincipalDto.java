package com.javamentor.qa.platform.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PrincipalDto {

    private String email;
    private String name;
    private String surname;
    private String avatarUrl;
    private String role;
}
