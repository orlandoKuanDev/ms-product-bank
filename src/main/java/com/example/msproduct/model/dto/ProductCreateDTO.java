package com.example.msproduct.model.dto;

import com.example.msproduct.model.entities.Rules;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCreateDTO {
    private String productName;
    private String productType;
    private Rules rules;
}
