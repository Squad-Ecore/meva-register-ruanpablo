package com.meva.finance.dto;

import com.meva.finance.model.UserMeva;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    public UserMeva converter(){
        return UserMeva.builder().cpf(cpf).name(name).genre(genre).birth(birth).state(state).city(city).build();
    }

}
