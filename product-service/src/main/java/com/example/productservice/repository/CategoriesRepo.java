package com.example.productservice.repository;

import com.example.productservice.entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriesRepo extends JpaRepository<Categories, Long> {

    Categories findCategoriesByTitleAndIsDeleted(String title, String isDeleted);
    Categories findCategoriesByTitle(String title);
    Categories findCategoriesById(Long id);
    List<Categories> findAllByIsDeleted(String isDeleted);


}
