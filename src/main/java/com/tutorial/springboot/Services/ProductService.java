package com.tutorial.springboot.Services;

import com.tutorial.springboot.DTO.ProductDTO;
import com.tutorial.springboot.DTO.ProductMapper;
import com.tutorial.springboot.Models.Product;
import com.tutorial.springboot.Repositories.ProductRepository;
import com.tutorial.springboot.Response.BasicResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public ResponseEntity<BasicResponse> getAllProducts(){
        List<Product> products = productRepository.findAll();
        if(products.isEmpty()){
            BasicResponse basicResponse = new BasicResponse();
            basicResponse.setSuccess(false);
            basicResponse.setErrorCode("");
            basicResponse.setMessage("empty");
            basicResponse.setResult(null);

            return new ResponseEntity<>(basicResponse, HttpStatus.NOT_FOUND);
        }
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (Product product: products) {
            productDTOS.add(ProductMapper.getINSTANCE().toDto(product));
        }
        BasicResponse basicResponse = new BasicResponse();
        basicResponse.setSuccess(true);
        basicResponse.setErrorCode("");
        basicResponse.setMessage("");
        basicResponse.setResult(productDTOS);

        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }

    public ResponseEntity<BasicResponse> getProducts(List<Integer> productIds){
        List<Product> products = productRepository.getProductsByIdNotIn(productIds);
        BasicResponse basicResponse = new BasicResponse();
        basicResponse.setSuccess(false);
        basicResponse.setErrorCode("");
        basicResponse.setMessage("");
        basicResponse.setResult(null);

        if(!products.isEmpty()){
            List<ProductDTO> productDTOS = new ArrayList<>();
            for (Product product: products) {
                productDTOS.add(ProductMapper.getINSTANCE().toDto(product));
            }

            basicResponse.setResult(productDTOS);
            basicResponse.setSuccess(true);

            return new ResponseEntity<>(basicResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }
}
