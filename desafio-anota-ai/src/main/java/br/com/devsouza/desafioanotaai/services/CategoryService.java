package br.com.devsouza.desafioanotaai.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.devsouza.desafioanotaai.domain.category.Category;
import br.com.devsouza.desafioanotaai.domain.category.CategoryDTO;
import br.com.devsouza.desafioanotaai.domain.category.exceptions.CategoryNotFoundException;
import br.com.devsouza.desafioanotaai.publishers.AwsSnsPublisher;
import br.com.devsouza.desafioanotaai.publishers.dtos.CatalogEventBody;
import br.com.devsouza.desafioanotaai.repositories.CategoryRepository;

@Service
public class CategoryService {
    
    private final Logger log = LoggerFactory.getLogger(AwsSnsPublisher.class);
    private final CategoryRepository repository;
    private final AwsSnsPublisher publisher;

    public CategoryService(CategoryRepository repository, AwsSnsPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    public Category insert(CategoryDTO data) {
        log.info("Inserindo categoria", data.title());
        Category newCategory = new Category(data);
        this.repository.save(newCategory);

        publisher.publish(CatalogEventBody.newCategory(newCategory));
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
        
        log.info("Atualizando categoria: ", id);
        if(!categoryData.title().isEmpty()) category.setTitle(categoryData.title());
        if(!categoryData.description().isEmpty()) category.setDescription(categoryData.description());
        
        this.repository.save(category);

        publisher.publish(CatalogEventBody.updateCategory(category));
        return category;
    }

    public void delete(String id) {
        Category category = this.repository.findById(id)
        .orElseThrow(CategoryNotFoundException::new);
        
        log.info("Deletando categoria: ", id);
        this.repository.delete(category);
        publisher.publish(CatalogEventBody.deleteCategory(category));
    } 
}
