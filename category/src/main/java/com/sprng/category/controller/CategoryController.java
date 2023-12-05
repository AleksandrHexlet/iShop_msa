package com.sprng.category.controller;

import com.sprng.category.model.Category;
import com.sprng.category.service.CategoryService;
import com.sprng.library.exception.IshopResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/name")
    public Mono<Category> getCategory(@RequestParam String name) {
        Mono<Category> category = categoryService.getCategory(name);
        return category;
    }

    @GetMapping
    public Flux<Category> getCategories() {
       return categoryService.getCategories();
    }

    @PostMapping
    public Mono<Category> createCategory(@RequestBody Category category) throws IshopResponseException {
        Mono<Category> newCategory = categoryService.createCategory(category);
        return newCategory;
    }

}
