package com.meva.finance.service;

import com.meva.finance.model.Category;
import com.meva.finance.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> listCategory() {
        return refiningCategory();
    }

    private List<Category> refiningCategory() {
        List<Category> list = categoryRepository.findAll();
        for (Category c : list) {
            List<String> palavras = List.of(c.getDescription().split(" "));
            palavras.forEach(p -> {
                if (p.length() < 3) palavras.remove(p);
            });
            c.setDescription(String.join(" ", palavras));
        }
        return categoryRepository.saveAll(list);
    }
}
