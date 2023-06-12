package com.meva.finance.dto;

import com.meva.finance.model.Family;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FamilyDtoTest {

    @Test
    void testConverterSucess(){
        FamilyDto familyDto = new FamilyDto(1L, "Souza");
        Family family = familyDto.converter();

        assertEquals(family.getDescription(), "Souza");
        assertEquals(family.getId(), 1L);
    }

}