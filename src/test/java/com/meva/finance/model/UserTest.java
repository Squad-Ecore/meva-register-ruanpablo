package com.meva.finance.model;

import com.meva.finance.dto.FamilyDto;
import com.meva.finance.dto.UserMevaDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testConverterSucess(){
        User user = new User("525263726372", "Ruan", "M", LocalDate.of(2002, 04,20),
                "SP", "São Paulo", new Family(1L, "Souza"));
        UserMevaDto userDto = user.converter();

        assertEquals(userDto.getName(), user.getName());
        assertEquals(userDto.getCpf(), user.getCpf());
    }
}