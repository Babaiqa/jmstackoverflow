package com.javamentor.qa.platform.models.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    private Long id;
    private String email;
    private String fullName;
    private String imageLink;
    private Integer reputationCount;
    private List<TagDto> tags;


}
