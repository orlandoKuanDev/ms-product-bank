package com.example.msproduct.mapper;

import com.example.msproduct.model.entities.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductConverter implements BaseConverter<Product, Object>{

    private final ModelMapper modelMapper;

    @Autowired
    public ProductConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Object> convertToDto(List<Product> entity, Object dto) {
        return null;
    }

    @Override
    public Page<Object> convertToDto(Page<Product> entity, Object dto) {
        return null;
    }

    @Override
    public Object convertToDto(Product entity, Object dto) {
        return modelMapper.map(entity, dto.getClass());
    }

    @Override
    public Product convertToEntity(Object dto, Product entity) {
        return modelMapper.map(dto, entity.getClass());
    }
}
