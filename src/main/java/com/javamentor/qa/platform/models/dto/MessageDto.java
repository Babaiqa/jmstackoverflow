package com.javamentor.qa.platform.models.dto;

import com.javamentor.qa.platform.models.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {

    private Long id;
    private String message;
    private LocalDateTime lastRedactionDate;
    private LocalDateTime persistDate;
    private User userSender;

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", lastRedactionDate=" + lastRedactionDate +
                ", persistDate=" + persistDate +
                ", userSender=" + userSender +
                '}';
    }
}
