package com.javamentor.qa.platform.models.dto;

import com.javamentor.qa.platform.models.entity.user.Role;
import lombok.*;

import java.io.Serializable;
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    private Long id;
    private String email;
    private String fullName;
    private String linkImage;
    private String city;
    private int reputation;
}
