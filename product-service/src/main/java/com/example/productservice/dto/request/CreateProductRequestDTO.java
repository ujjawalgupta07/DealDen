package com.example.productservice.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductRequestDTO {
    
    private String title; 
    private String price; 
    private String category; 
    private String description; 
    private String image;
}
