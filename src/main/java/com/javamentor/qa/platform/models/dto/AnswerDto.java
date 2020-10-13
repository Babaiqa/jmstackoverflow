package com.javamentor.qa.platform.models.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class AnswerDto implements Serializable {
    private Long id;
    private Long user_id;
    private Long question_id;
    private String body;
    private LocalDateTime persist_date;
    private Boolean is_helpful;
    private LocalDateTime date_accept;
    private int count_valuable;

    public AnswerDto() {
        super();
    }

    public AnswerDto(Long id, Long user_id, Long question_id, String body, LocalDateTime persist_date,
                                        Boolean is_helpful, LocalDateTime date_accept, int count_valuable) {
        this.id = id;
        this.user_id = user_id;
        this.question_id = question_id;
        this.body = body;
        this.persist_date = persist_date;
        this.is_helpful = is_helpful;
        this.date_accept = date_accept;
        this.count_valuable = count_valuable;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(Long question_id) {
        this.question_id = question_id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getPersist_date() {
        return persist_date;
    }

    public void setPersist_date(LocalDateTime persist_date) {
        this.persist_date = persist_date;
    }

    public Boolean getIs_helpful() {
        return is_helpful;
    }

    public void setIs_helpful(Boolean is_helpful) {
        this.is_helpful = is_helpful;
    }

    public LocalDateTime getDate_accept() {
        return date_accept;
    }

    public void setDate_accept(LocalDateTime date_accept) {
        this.date_accept = date_accept;
    }

    public int getCount_valuable() {
        return count_valuable;
    }

    public void setCount_valuable(int count_valuable) {
        this.count_valuable = count_valuable;
    }
}
