package com.sprng.category.controller;

import com.sprng.category.model.Category;
import com.sprng.category.service.CategoryService;
import com.sprng.library.exception.IshopResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
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
        return category.doOnError(throwable -> {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT,
                    throwable.getMessage());
        });
    }

    @GetMapping
    public Flux<Category> getCategories() {
        return categoryService.getCategories().doOnError(throwable -> {
            throw new ResponseStatusException(HttpStatus
                    .NO_CONTENT, throwable.getMessage());
        }); //если Mono отработало с ошибкой.Вернули ошибку клиенту
    }

    @PostMapping
    public Mono<Category> createCategory(@RequestBody Category category) throws IshopResponseException {
        Mono<Category> newCategory = categoryService.createCategory(category);
        return newCategory.doOnError(throwable -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    throwable.getMessage());
        }); //если Mono отработало с ошибкой.Вернули ошибку клиенту
    }

    @GetMapping("/exists/id/{categoryID}")
    public Mono<Boolean> existsByCategoryID(@PathVariable int categoryID) {
        Mono<Boolean> existsCategory = categoryService.existsByCategoryID(categoryID);
        return existsCategory;
        };
    }


