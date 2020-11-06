package com.javamentor.qa.platform.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TagDto implements Serializable {

    private static final long serialVersionUID = -8087563918115872879L;

    private Long id;
    private String name;

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }

}
