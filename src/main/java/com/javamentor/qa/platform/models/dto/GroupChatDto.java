package com.javamentor.qa.platform.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupChatDto {

    private Long id;
    private Long chatId;
    private Set<UserDto> users;

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", chat=" + chatId +
                ", users=" + users +
                '}';
    }
}
