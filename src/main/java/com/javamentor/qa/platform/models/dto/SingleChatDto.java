package com.javamentor.qa.platform.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SingleChatDto implements Serializable {

    private Long id;
    private String title;
    private Long userSenderId;
    private Long userRecipientId;
    private String nickname;
    private String imageLink;
    private String message;
    private Timestamp lastRedactionDate;
    private Long userSenderIdCheck;

    @Override
    public String toString() {
        return "SingleChatDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", userSender=" + userSenderId +
                ", userRecipient=" + userRecipientId +
                ", nickname='" + nickname + '\'' +
                ", imageLink='" + imageLink + '\'' +
                ", message='" + message + '\'' +
                ", lastRedactionDate=" + lastRedactionDate +
                ", userSenderIdCheck=" + userSenderIdCheck +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SingleChatDto that = (SingleChatDto) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(userSenderId, that.userSenderId) && Objects.equals(userRecipientId, that.userRecipientId) && Objects.equals(nickname, that.nickname) && Objects.equals(imageLink, that.imageLink) && Objects.equals(message, that.message) && Objects.equals(lastRedactionDate, that.lastRedactionDate) && Objects.equals(userSenderIdCheck, that.userSenderIdCheck);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, userSenderId, userRecipientId, nickname, imageLink, message, lastRedactionDate, userSenderIdCheck);
    }
}
