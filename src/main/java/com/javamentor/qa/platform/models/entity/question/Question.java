package com.javamentor.qa.platform.models.entity.question;

import com.javamentor.qa.platform.exception.ConstrainException;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.UserFavoriteQuestion;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "question")
public class Question implements Serializable {

    private static final long serialVersionUID = -4612026867697897418L;
    @Id
    @GeneratedValue(generator = "Question_seq")
    private Long id;

    @Column
    @NotNull
    private String title;


    @Min(0)
    @Column(name = "view_count")
    private Integer viewCount = 0;


    @NotNull
    @Column
    @Type(type = "org.hibernate.type.TextType")
    private String description;

    @CreationTimestamp
    @Column(name = "persist_date", updatable = false)
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    private LocalDateTime persistDateTime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "question_has_tag",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private  List<Tag> tags;

    @Column(name = "last_redaction_date", nullable = false)
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    @UpdateTimestamp
    private LocalDateTime lastUpdateDateTime;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "question", orphanRemoval = true)
    private List<Answer> answers;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "question", orphanRemoval = true)
    private List<CommentQuestion> commentQuestions;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "question", orphanRemoval = true)
    private List<UserFavoriteQuestion> userFavoriteQuestions;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "question", orphanRemoval = true)
    private List<VoteQuestion> voteQuestions;

    @PrePersist
    private void prePersistFunction() {
        checkConstraints();
    }

    @PreUpdate
    private void preUpdateFunction() {
        checkConstraints();
    }

    private void checkConstraints() {
        if (this.tags == null || this.tags.isEmpty()) {
            throw new ConstrainException("Экземпляр Question должен иметь в поле tags хотя бы один элемент");
        }
        if (this.isDeleted == null) {
            this.isDeleted = false;
        }
        try {
            if (this.user.getId() <= 0) {
                throw new EntityNotFoundException("User id must be > 0 on create or update.");
            }
        } catch (NullPointerException e) {
            throw new EntityNotFoundException("User id must be not null on create.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;
        Question question = (Question) o;
        return Objects.equals(id, question.id) &&
                Objects.equals(title, question.title) &&
                Objects.equals(viewCount, question.viewCount) &&
                Objects.equals(description, question.description) &&
                Objects.equals(persistDateTime, question.persistDateTime) &&
                Objects.equals(user, question.user) &&
                Objects.equals(tags, question.tags) &&
                Objects.equals(lastUpdateDateTime, question.lastUpdateDateTime) &&
                Objects.equals(isDeleted, question.isDeleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, viewCount, description, persistDateTime, user, tags, lastUpdateDateTime, isDeleted);
    }
}
