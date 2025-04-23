package com.example.productservice.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class CategoriesDTO {

    private Integer id;
    private String title;

    public CategoriesDTO(Integer id, String title) {
        this.id = id;
        this.title = title;
    }

    public CategoriesDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
