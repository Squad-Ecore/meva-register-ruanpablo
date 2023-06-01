package com.meva.finance.dto;

import com.meva.finance.model.SubCategory;
import com.meva.finance.repository.SubCategoryRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DescriptionDto {
    private String description;

    public SubCategory converterSubCategory(Long id, SubCategoryRepository sub){
        return SubCategory.builder().description(description).category(sub.findById(id).get().getCategory()).build();
    }
}
