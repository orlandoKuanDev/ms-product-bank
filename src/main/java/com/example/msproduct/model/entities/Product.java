package com.example.msproduct.model.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "product")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class Product {
    @Id
    private String id;

    @Field(name = "productName")
    private String productName;

    @Field(name = "productType")
    private String productType;

    @Field(name = "rules")
    private Rules rules;
}
