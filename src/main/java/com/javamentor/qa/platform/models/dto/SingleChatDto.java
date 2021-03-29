package com.javamentor.qa.platform.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SingleChatDto {

    private Long id;
    private Long chatId;
    private String title;
    private Long userOneId;
    private Long userTwoId;

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", title='" + title + '\'' +
                ", userOneId=" + userOneId +
                ", userTwoId=" + userTwoId +
                '}';
    }
}
