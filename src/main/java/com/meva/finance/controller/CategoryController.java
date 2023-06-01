package com.meva.finance.controller;

import com.meva.finance.dto.DescriptionDto;
import com.meva.finance.model.Category;
import com.meva.finance.service.CategoryService;
import com.meva.finance.validation.ValidException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController()
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ResponseEntity<List<Category>> list(){
        return ResponseEntity.ok(categoryService.listCategory());
    }

//    @PostMapping("/register")
//    public ResponseEntity<Long> register(@RequestBody DescriptionDto dto) throws ValidException {
//        return ResponseEntity.ok(categoryService.register(dto));
//    }

    @GetMapping("/getId")
    public ResponseEntity<Long> getId(@RequestBody DescriptionDto dto) throws ValidException {
        return ResponseEntity.ok(categoryService.getId(dto));
    }


}
