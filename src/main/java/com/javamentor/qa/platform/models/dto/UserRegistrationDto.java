package com.javamentor.qa.platform.models.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
//@NoArgsConstructor
public class UserRegistrationDto  implements Serializable {
    public String fullName;
    public String email;
    public String password;
}
