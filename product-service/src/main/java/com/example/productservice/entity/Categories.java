package com.example.productservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Categories extends BaseModel implements Serializable {

    private String title;

    @JsonIgnore
    @OneToMany(mappedBy = "category" , cascade = {CascadeType.REMOVE})
    private List<Products> products;

}
