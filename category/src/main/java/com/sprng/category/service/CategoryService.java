package com.sprng.category.service;

import com.sprng.category.model.Category;
import com.sprng.category.repository.CategoryRepository;
import com.sprng.library.exception.IshopResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Mono<Category> getCategory(String nameCategory) {
        return categoryRepository.findByName(nameCategory);
    }

    public Mono<Category> createCategory(Category category) throws IshopResponseException {
        if(categoryRepository.findByName(category.getName()) != null) throw new IshopResponseException("category is exist");
    return categoryRepository.save(category);
    }

    public Flux<Category> getCategories() {
   return categoryRepository.findAll();
    }
}
