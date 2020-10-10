package com.javamentor.qa.platform.models.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "badges")
public class Badges {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String badges;

    @Column(name = "reputations_for_merit")
    private Integer reputationForMerit;

    @Column
    private String description;
}
