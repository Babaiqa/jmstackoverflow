package com.javamentor.qa.platform.models.dto;

import com.javamentor.qa.platform.models.entity.chat.Chat;
import com.javamentor.qa.platform.models.entity.user.User;
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
    private Chat chat;
    private User userOne;
    private User userTwo;

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", chat=" + chat +
                ", userOne=" + userOne +
                ", userTwo=" + userTwo +
                '}';
    }
}
