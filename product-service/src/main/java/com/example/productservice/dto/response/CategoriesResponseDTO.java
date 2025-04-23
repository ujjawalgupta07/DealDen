package com.example.productservice.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CategoriesResponseDTO {

    private Long id;
    private String title;

}
