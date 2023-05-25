package com.meva.finance.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter @Setter
public class UserMevaDto {

    @Size(min = 11, max = 11)
    private String cpf;
    @NotEmpty @Size(max = 250)
    private String name;
    @NotEmpty @Size(max = 1)
    private String genre;
    @NotNull
    private LocalDate birth;
    @NotEmpty @Size(max = 100)
    private String state;
    @NotEmpty @Size(max = 100)
    private String city;
    @NotEmpty @ManyToOne
    private String family;

}
