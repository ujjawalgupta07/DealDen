package com.example.productservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories",
        uniqueConstraints = @UniqueConstraint(columnNames = "title"))
public class Category extends BaseModel {

    /**
     * 1. @Builder + @NoArgsConstructor for immutability and clean instantiation
     * 2. Proper cascade and Orphan Removal setup on OneToMany
     * 3. Unique Constraint directly on category title
     *
     */

    private String title;

    @JsonIgnore
    @OneToMany(mappedBy = "category" , cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    public Category() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
