package com.example.productservice.dto.response;

import lombok.*;

public class CategoryResponseDTO {

    private Long id;
    private String title;

    public CategoryResponseDTO(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public CategoryResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
