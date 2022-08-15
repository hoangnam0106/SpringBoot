package com.example.SpringBoot.Services;

import com.example.SpringBoot.Models.Product;
import com.example.SpringBoot.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public Product save(Product product){
        if(productRepository.existsByName(product.getName())){
            return null;
        }else {
            return productRepository.save(product);
        }

    }

    public List<Product> findByName(String name){
        return productRepository._findByName(name);
    }

    public Product updateProduct(Product product){
        Optional<Product> productFound = productRepository.findById(product.getId());
        productFound.ifPresent(product1 -> {
            product1.setName(product.getName());
            product1.setPrice(product.getPrice());
            product1.setVat(product.getVat());

            productRepository.save(product1);
        });
        return product;
    }
}
