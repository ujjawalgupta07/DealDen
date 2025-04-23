package com.example.productservice.repository;

import com.example.productservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findCategoriesByTitleAndIsDeleted(String title, String isDeleted);
    Category findCategoriesByTitle(String title);
    Category findCategoriesById(Long id);
    List<Category> findAllByIsDeleted(String isDeleted);


}
