package com.example.productservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Products extends BaseModel {

    @Column(nullable = false)
    private String title;
    private String description;
    @Column(nullable = false)
    private BigDecimal price;
    private String imageUrl;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "category_id", nullable = false)
    private Categories category;
}
