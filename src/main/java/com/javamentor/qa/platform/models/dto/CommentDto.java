package com.javamentor.qa.platform.models.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class CommentDto implements Serializable {
    private Long id;
    private Long user_id;
    private String text;
    private LocalDateTime persist_date;
    private LocalDateTime last_redaction_date;
    private int conmment_type;

    public CommentDto() {
        super();
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getPersist_date() {
        return persist_date;
    }

    public void setPersist_date(LocalDateTime persist_date) {
        this.persist_date = persist_date;
    }

    public LocalDateTime getLast_redaction_date() {
        return last_redaction_date;
    }

    public void setLast_redaction_date(LocalDateTime last_redaction_date) {
        this.last_redaction_date = last_redaction_date;
    }

    public int getConmment_type() {
        return conmment_type;
    }

    public void setConmment_type(int conmment_type) {
        this.conmment_type = conmment_type;
    }
}
