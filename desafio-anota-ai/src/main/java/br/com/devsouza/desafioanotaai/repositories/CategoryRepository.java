package br.com.devsouza.desafioanotaai.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.devsouza.desafioanotaai.domain.category.Category;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
    
}
