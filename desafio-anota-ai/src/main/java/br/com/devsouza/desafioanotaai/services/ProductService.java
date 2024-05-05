package br.com.devsouza.desafioanotaai.services;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.devsouza.desafioanotaai.domain.category.exceptions.CategoryNotFoundException;
import br.com.devsouza.desafioanotaai.domain.product.Product;
import br.com.devsouza.desafioanotaai.domain.product.ProductDTO;
import br.com.devsouza.desafioanotaai.domain.product.exceptions.ProductNotFoundException;
import br.com.devsouza.desafioanotaai.repositories.ProductRepository;

@Service
public class ProductService {
    
    private final CategoryService categoryService;
    private final ProductRepository productRepository;

    public ProductService(CategoryService categoryService, ProductRepository productRepository) {
        this.categoryService = categoryService;
        this.productRepository = productRepository;
    }

    public Product insert(ProductDTO data) {
        categoryService.getById(data.categoryId())
            .orElseThrow(CategoryNotFoundException::new);

        Product newProduct = new Product(data);
        
        this.productRepository.save(newProduct);

        return newProduct;
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product update(String id, ProductDTO data) {
        Product product = this.productRepository.findById(id)
            .orElseThrow(ProductNotFoundException::new);

        if(data.categoryId() != null) {
            categoryService.getById(data.categoryId())
                .orElseThrow(CategoryNotFoundException::new);

            product.setCategory(data.categoryId());
        }

        if(!data.title().isEmpty()) product.setTitle(data.title());
        if(!data.description().isEmpty()) product.setDescription(data.description());
        if(!(data.price() == null)) product.setPrice(data.price());
        
        this.productRepository.save(product);

        return product;
    }

    public void delete(String id) {
        Product obj = this.productRepository.findById(id)
            .orElseThrow(ProductNotFoundException::new);
        
        this.productRepository.delete(obj);
    }
    
}
