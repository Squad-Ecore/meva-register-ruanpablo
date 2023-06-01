package com.meva.finance.model;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Builder
@Entity
@Table(name = "category")
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_id_category_seq")
    @SequenceGenerator(name = "category_id_category_seq", sequenceName = "category_id_category_seq", allocationSize = 1)
    @Column(name = "id_category")
    private Long id;
    private String description;
}
