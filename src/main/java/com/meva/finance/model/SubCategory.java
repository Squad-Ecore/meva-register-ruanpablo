package com.meva.finance.model;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sub_category")
public class SubCategory {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sub_category_id_sub_category_seq")
    @SequenceGenerator(name = "sub_category_id_sub_category_seq", sequenceName = "sub_category_id_sub_category_seq", allocationSize = 1)
    @Column(name = "id_sub_category")
    private Long id;
    private String description;
    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category category;

}
