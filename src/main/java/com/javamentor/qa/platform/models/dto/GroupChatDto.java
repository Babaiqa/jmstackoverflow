package com.javamentor.qa.platform.models.dto;

import com.javamentor.qa.platform.models.entity.chat.Chat;
import com.javamentor.qa.platform.models.entity.user.User;
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
    private Chat chat;
    private Set<User> users;

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", chat=" + chat +
                ", users=" + users +
                '}';
    }
}
