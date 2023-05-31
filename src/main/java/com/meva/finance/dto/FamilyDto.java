package com.meva.finance.dto;

import com.meva.finance.model.Family;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FamilyDto {
    private Long id;
    private String description;

    public Family converter(){
        return Family.builder().id(id).description(description).build();
    }

}

