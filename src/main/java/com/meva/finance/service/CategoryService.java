package com.meva.finance.service;

import com.meva.finance.dto.DescriptionDto;
import com.meva.finance.model.Category;
import com.meva.finance.model.SubCategory;
import com.meva.finance.repository.CategoryRepository;
import com.meva.finance.repository.SubCategoryRepository;
import com.meva.finance.validation.ValidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SubCategoryRepository subCategoryRepository;

    public List<Category> listCategory() {
        return categoryRepository.findAll();
    }

//    public Long register(DescriptionDto dto) throws ValidException {
////        List<String> palavras = new ArrayList<>(List.of(dto.getDescription().split(" ")));
////        palavras.removeIf(p -> p.length() < 3);
//////        dto.setDescription(String.join(" ", palavras));
////        SubCategory subcategory = subCategoryRepository.save(dto.converterSubCategory(6L, subCategoryRepository));
////        palavras.forEach(p -> subCategoryRepository.findByDescription(p).ifPresent(sub -> {
////            subcategory.getCategory().setId(sub.getCategory().getId());
////            subCategoryRepository.save(subcategory);
////        }));
////        return subcategory.getCategory().getId();
//    }

    private void validDto(DescriptionDto dto) throws ValidException {
        if ((dto.getDescription().isEmpty()) || dto.getDescription().length() < 3)
            throw new ValidException("Descrição insuficiente para pesquisa.");
    }

    public Long getId(DescriptionDto dto) throws ValidException{
        validDto(dto);
        List<String> palavras = new ArrayList<>(List.of(dto.getDescription().split(" ")));
        palavras.removeIf(p -> p.length() < 3);
        AtomicLong id = new AtomicLong(6L);
        palavras.forEach(p -> subCategoryRepository.findByDescription(p).ifPresent(sub -> id.set(sub.getCategory().getId())));
        return id.get();
    }
}
