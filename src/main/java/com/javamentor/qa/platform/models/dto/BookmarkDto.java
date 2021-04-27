package com.javamentor.qa.platform.models.dto;

import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkDto implements Serializable {
    private Long id;
    private User user;
    private Question question;

    @Override
    public String toString() {
        return "BookmarkDto{" +
                "id=" + id +
                ", user=" + user +
                ", question=" + question +
                '}';
    }
}
