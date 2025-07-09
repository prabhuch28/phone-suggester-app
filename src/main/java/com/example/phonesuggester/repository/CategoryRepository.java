package com.example.phonesuggester.repository;

import com.example.phonesuggester.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
 
public interface CategoryRepository extends MongoRepository<Category, String> {
    boolean existsByName(String name);
} 