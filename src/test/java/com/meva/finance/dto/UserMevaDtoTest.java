package com.meva.finance.dto;

import com.meva.finance.model.Family;
import com.meva.finance.model.User;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserMevaDtoTest {

    @Test
    void testConverterSucess(){
        UserMevaDto userMevaDto = new UserMevaDto("525263726372", "Ruan", "M", LocalDate.of(2002, 04,20),
                "SP", "São Paulo", new FamilyDto(1L, "Souza"));
        User user = userMevaDto.converter();

        assertEquals(user.getName(), userMevaDto.getName());
        assertEquals(user.getCpf(), userMevaDto.getCpf());
    }

}