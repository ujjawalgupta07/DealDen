package com.example.orderservice.dto.response;

import java.math.BigDecimal;

public class ValidateProductResponseDTO {

    private Long productId;
    private String title;
    private BigDecimal price;

    public ValidateProductResponseDTO() {
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
