package com.example.phonesuggester.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Document(collection = "categories")
public class Category {
    @Id
    private String id;

    @NotBlank
    @Size(min = 3, max = 50)
    private String name;

    @Size(max = 200)
    private String description;

    public Category() {}
    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
} 