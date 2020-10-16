package com.javamentor.qa.platform.models.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TagDto implements Serializable {
    private static final long serialVersionUID = -8087563918115872879L;
    private Long id;
    private String name;
}
