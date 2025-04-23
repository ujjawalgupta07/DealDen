package com.example.productservice.repository;

import com.example.productservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findCategoryByTitleAndIsDeleted(String title, String isDeleted);
    Category findCategoryByTitle(String title);
    Category findCategoryById(Long id);
    List<Category> findAllByIsDeleted(String isDeleted);


}
