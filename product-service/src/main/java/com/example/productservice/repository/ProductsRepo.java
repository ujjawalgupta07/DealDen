package com.example.productservice.repository;

import com.example.productservice.entity.Products;
import com.example.productservice.repository.projection.ProductsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepo extends JpaRepository<Products, Long> {

    List<Products> findProductsByIsDeleted(String isDeleted);

    Products findProductsById(Long id);

    Products findProductsByDescription(String desc);

    Products findProductsByTitle(String title);

    Products save(Products p);

    Products updateIsDeletedById(Long id);

    Products findProductsByIdAndTitle(Long id, String title);

    @Query("select p.id, p.title, p.price from Products p where p.id = :id")
    ProductsProjection getTitleAndPriceProductById(@Param("id") Long id);
}
