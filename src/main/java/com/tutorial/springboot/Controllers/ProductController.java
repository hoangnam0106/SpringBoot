package com.example.SpringBoot.Controllers;

import com.example.SpringBoot.Models.Product;
import com.example.SpringBoot.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping(value = "/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<Product> save(@RequestBody Product product){
        Product productSave = productService.save(product);
        return new ResponseEntity<>(productSave, HttpStatus.OK);
    }

    @RequestMapping(value = "/search/{productName}", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> findByItem(@PathVariable String productName){
        List<Product> productsFound = productService.findByName(productName);
        return new ResponseEntity<>(productsFound, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<Product> updateProduct(@RequestBody Product product){
        Product productUpdate = productService.updateProduct(product);
        return new ResponseEntity<>(productUpdate, HttpStatus.OK);
    }
}
