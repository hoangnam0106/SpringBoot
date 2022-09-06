package com.tutorial.springboot.DTO;

import com.tutorial.springboot.Models.Product;

public class ProductMapper {
    private static ProductMapper INSTANCE;

    public static ProductMapper getINSTANCE(){
        if(INSTANCE == null){
            INSTANCE = new ProductMapper();
        }
        return INSTANCE;
    }

    public Product toEntity(ProductDTO dto){
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setVat(dto.getVat());

        return product;
    }

    public ProductDTO toDto(Product product){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setVat(product.getVat());

        return productDTO;
    }
}
