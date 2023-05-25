package com.meva.finance.model;

import com.meva.finance.dto.UserMevaDto;
import com.meva.finance.repository.FamilyRepository;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter @Setter
@Entity
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
    private Family family;

    public UserMeva() {
    }

    public UserMeva(UserMevaDto userDto, FamilyRepository familyRepository) {
        this.cpf = userDto.getCpf();
        this.name = userDto.getName();
        this.genre = userDto.getGenre();
        this.birth = userDto.getBirth();
        this.state = userDto.getState();
        this.city = userDto.getCity();
        this.family = familyRepository.save(new Family(userDto.getFamily()));
    }

    @Override
    public String toString() {
        return "UserMeva{" +
                "cpf='" + cpf + '\'' +
                ", name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", birth=" + birth +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", family=" + family +
                '}';
    }
}
