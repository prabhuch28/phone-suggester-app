package com.example.phonesuggester.repository;

import com.example.phonesuggester.model.Phone;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface PhoneRepository extends MongoRepository<Phone, String> {
    List<Phone> findByUsageTypesContaining(String usageType);
    List<Phone> findByBrand(String brand);
}