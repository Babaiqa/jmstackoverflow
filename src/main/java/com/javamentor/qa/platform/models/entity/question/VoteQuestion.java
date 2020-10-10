package com.javamentor.qa.platform.models.entity.question;

import com.javamentor.qa.platform.models.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "votes_on_questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoteQuestion {

    @EmbeddedId
    private VoteQuestionPK voteQuestionPK;

    public VoteQuestion(User user, Question question, int vote) {
        this.vote = vote;
        this.voteQuestionPK = new VoteQuestionPK(user, question);
    }

    private int vote;

    @PrePersist
    private void prePersistFunction() {
        checkConstraints();
    }

    private void checkConstraints(){
        if (vote != 1 && vote != -1){
            throw new RuntimeException("В сущности VoteQuestion допускается передача значения в поле vote только 1 или -1");
        }
    }

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class VoteQuestionPK implements Serializable {

        @ManyToOne
        private User user;

        @ManyToOne
        private Question question;

        @Column(name = "persist_date", updatable = false)
        @Type(type = "org.hibernate.type.LocalDateTimeType")
        private LocalDateTime localDateTime = LocalDateTime.now();

        public VoteQuestionPK(User user, Question question) {
            this.user = user;
            this.question = question;
        }
    }
}
