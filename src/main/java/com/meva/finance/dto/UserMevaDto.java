package com.meva.finance.dto;

import com.meva.finance.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserMevaDto {

    private String cpf;
    private String name;
    private String genre;
    private LocalDate birth;
    private String state;
    private String city;
    private FamilyDto familyDto;

    public User converter(){
        return User.builder().cpf(cpf).name(name).genre(genre).birth(birth).state(state).city(city).build();
    }

}
