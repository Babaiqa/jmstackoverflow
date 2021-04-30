package com.javamentor.qa.platform.models.entity.question;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "question_has_tag")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionHasTag implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    private Tag tag;

    public QuestionHasTag(Question question, Tag tag) {
        this.question = question;
        this.tag = tag;
    }


}
