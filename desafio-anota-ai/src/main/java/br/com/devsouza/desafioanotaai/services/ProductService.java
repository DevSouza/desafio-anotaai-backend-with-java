package br.com.devsouza.desafioanotaai.services;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.devsouza.desafioanotaai.domain.category.Category;
import br.com.devsouza.desafioanotaai.domain.category.exceptions.CategoryNotFoundException;
import br.com.devsouza.desafioanotaai.domain.product.Product;
import br.com.devsouza.desafioanotaai.domain.product.ProductDTO;
import br.com.devsouza.desafioanotaai.domain.product.exceptions.ProductNotFoundException;
import br.com.devsouza.desafioanotaai.repositories.ProductRepository;
import br.com.devsouza.desafioanotaai.services.aws.AwsSnsService;
import br.com.devsouza.desafioanotaai.services.aws.MessageDTO;

@Service
public class ProductService {
    
    private final CategoryService categoryService;
    private final ProductRepository productRepository;
    private final AwsSnsService snsService;

    public ProductService(CategoryService categoryService, ProductRepository productRepository, AwsSnsService snsService) {
        this.categoryService = categoryService;
        this.productRepository = productRepository;
        this.snsService = snsService;
    }

    public Product insert(ProductDTO data) {
        Category category = categoryService.getById(data.categoryId())
            .orElseThrow(CategoryNotFoundException::new);

        Product product = new Product(data);
        product.setCategory(category);

        this.productRepository.save(product);

        snsService.publish(new MessageDTO(product.getOwnerId()));

        return product;
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product update(String id, ProductDTO data) {
        Product product = this.productRepository.findById(id)
            .orElseThrow(ProductNotFoundException::new);

        if(data.categoryId() != null) {
            categoryService.getById(data.categoryId())
                .ifPresent(product::setCategory);
        }

        if(!data.title().isEmpty()) product.setTitle(data.title());
        if(!data.description().isEmpty()) product.setDescription(data.description());
        if(!(data.price() == null)) product.setPrice(data.price());
        
        this.productRepository.save(product);

        snsService.publish(new MessageDTO(product.getOwnerId()));

        return product;
    }

    public void delete(String id) {
        Product obj = this.productRepository.findById(id)
            .orElseThrow(ProductNotFoundException::new);
        
        this.productRepository.delete(obj);
    }
    
}