package com.example.productservice.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

public class CreateProductRequestDTO {

    @NotBlank(message = "Product title is required.")
    private String title;

    @NotNull(message = "Price is required.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero.")
    private BigDecimal price;

    @NotNull(message = "Category title is required.")
    private String categoryTitle;

    @Size(max = 500)
    private String description;

    private String image;

    public CreateProductRequestDTO(String title, BigDecimal price, String categoryTitle, String description, String image) {
        this.title = title;
        this.price = price;
        this.categoryTitle = categoryTitle;
        this.description = description;
        this.image = image;
    }

    public CreateProductRequestDTO() {
    }

    public @NotBlank(message = "Product title is required.") String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank(message = "Product title is required.") String title) {
        this.title = title;
    }

    public @NotNull(message = "Price is required.") @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero.") BigDecimal getPrice() {
        return price;
    }

    public void setPrice(@NotNull(message = "Price is required.") @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero.") BigDecimal price) {
        this.price = price;
    }

    public @NotNull(message = "Category title is required.") String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(@NotNull(message = "Category title is required.") String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public @Size(max = 500) String getDescription() {
        return description;
    }

    public void setDescription(@Size(max = 500) String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
