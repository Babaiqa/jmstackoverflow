package com.javamentor.qa.platform.models.dto;

import java.io.Serializable;

public class TagQuestionDto implements Serializable {
    Long id;
    String name;

    public TagQuestionDto() {
        super();
    }

    public TagQuestionDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}





