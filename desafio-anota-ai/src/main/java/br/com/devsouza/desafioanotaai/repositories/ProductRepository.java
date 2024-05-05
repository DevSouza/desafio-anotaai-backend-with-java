package br.com.devsouza.desafioanotaai.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.devsouza.desafioanotaai.domain.product.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
    
}
