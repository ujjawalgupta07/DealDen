package com.example.productservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class BaseModel implements Serializable {

    /**
     * 1. BaseModel for common fields to follow DRY.
     * 2. Audit support with Spring's @CreatedBy, @LastModifiedDate etc.
     *
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreatedDate
    @Column(updatable = false)
    private LocalDate createdAt;
    @LastModifiedDate
    private LocalDate updatedAt;
    @CreatedBy
    private String createdBy;
    @LastModifiedBy
    private String updatedBy;
    private String isDeleted;
}
