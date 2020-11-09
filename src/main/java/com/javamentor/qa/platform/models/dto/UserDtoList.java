package com.javamentor.qa.platform.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoList {
    private Long id;
    private String fullName;
    private String linkImage;
    private Long reputation;
    private List<TagDto> tags;

    public UserDtoList(Long id, String fullName, String linkImage, Long reputation) {
        this.id = id;
        this.fullName = fullName;
        this.linkImage = linkImage;
        this.reputation = reputation;
    }
}
