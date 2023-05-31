package com.meva.finance.model;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Builder
@Entity
@Table(name = "family")
public class Family {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "family_id_family_seq")
    @SequenceGenerator(name = "family_id_family_seq", sequenceName = "family_id_family_seq", allocationSize = 1)
    @Column(name = "id_family")
    private Long id;
    private String description;
}
