package com.example.productservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CreateCategoryRequestDTO {

    @NotBlank(message = "Category title is required.")
    @Size(max = 100, message = "Category title can be at most 100 characters.")
    private String title;

    public CreateCategoryRequestDTO(String title) {
        this.title = title;
    }

    public CreateCategoryRequestDTO() {
    }

    public @NotBlank(message = "Category title is required.") @Size(max = 100, message = "Category title can be at most 100 characters.") String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank(message = "Category title is required.") @Size(max = 100, message = "Category title can be at most 100 characters.") String title) {
        this.title = title;
    }
}
