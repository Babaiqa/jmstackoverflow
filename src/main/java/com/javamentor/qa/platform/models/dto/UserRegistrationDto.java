package com.javamentor.qa.platform.models.dto;

import java.io.Serializable;

public class UserRegistrationDto  implements Serializable {
    public String fullName;
    public String email;
    public String password;

    public UserRegistrationDto() {
        super();
    }

    public UserRegistrationDto(String fullName, String email, String password) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

//    UserRegistrationDto {
//
//    }
//
//    UserDto {
//
//    }
