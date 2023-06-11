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
public class User {

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

    public UserMevaDto converter(){
        return UserMevaDto.builder().cpf(cpf).name(name).genre(genre).birth(birth).state(state).city(city).build();
    }

}
