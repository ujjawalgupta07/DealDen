package com.example.productservice.repository;

import com.example.productservice.entity.Product;
import com.example.productservice.repository.projection.ProductsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findProductsByIsDeleted(String isDeleted);

    Product findProductsById(Long id);

    Product findProductsByDescription(String desc);

    Product findProductsByTitle(String title);

    Product save(Product p);

    Product findProductsByIdAndTitle(Long id, String title);

    @Query("select p.id, p.title, p.price from Product p where p.id = :id")
    ProductsProjection getTitleAndPriceProductById(@Param("id") Long id);
}
