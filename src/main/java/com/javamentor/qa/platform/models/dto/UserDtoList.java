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
    private String fullname;
    private String linkImage;
    private Long reputation;
    private List<TagDto> tags; // 3 должен вывести 3 основных тэга в которых участвовал пользователь

    public UserDtoList(Long id, String fullname, String linkImage, Long reputation) {
        this.id = id;
        this.fullname = fullname;
        this.linkImage = linkImage;
        this.reputation = reputation;
    }
}
