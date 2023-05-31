package com.meva.finance.model;

import com.meva.finance.dto.UserMevaDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "user_meva")
public class UserMeva {

    @Id @Column(unique = true)
    private String cpf;
    private String name;
    private String genre;
    private LocalDate birth;
    private String state;
    private String city;
    @ManyToOne
    @JoinColumn(name = "id_family")
    private Family family;

}
