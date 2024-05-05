package br.com.devsouza.desafioanotaai.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.devsouza.desafioanotaai.domain.category.Category;
import br.com.devsouza.desafioanotaai.domain.category.CategoryDTO;
import br.com.devsouza.desafioanotaai.domain.category.exceptions.CategoryNotFoundException;
import br.com.devsouza.desafioanotaai.repositories.CategoryRepository;

@Service
public class CategoryService {
    
    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public Category insert(CategoryDTO data) {
        Category newCategory = new Category(data);
        this.repository.save(newCategory);

        return newCategory;
    }

    public List<Category> getAll() {
        return repository.findAll();
    }

    public Optional<Category> getById(String id) {
        return repository.findById(id);
    }

    public Category update(String id, CategoryDTO categoryData) {
        Category category = this.repository.findById(id)
            .orElseThrow(CategoryNotFoundException::new);

        if(!categoryData.title().isEmpty()) category.setTitle(categoryData.title());
        if(!categoryData.description().isEmpty()) category.setDescription(categoryData.description());
        
        this.repository.save(category);

        return category;
    }

    public void delete(String id) {
        Category category = this.repository.findById(id)
            .orElseThrow(CategoryNotFoundException::new);
        
        this.repository.delete(category);
    } 
}
