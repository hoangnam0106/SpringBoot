package com.tutorial.springboot.Controllers;

import com.tutorial.springboot.Models.Product;
import com.tutorial.springboot.Response.BasicResponse;
import com.tutorial.springboot.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin(value = "http://localhost:4200")
@RequestMapping(value = "/api/v1/product")
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

    @RequestMapping(value = "/get-all", method = RequestMethod.GET)
    public ResponseEntity<BasicResponse> getAllProducts(){
        return productService.getAllProducts();
    }

    @RequestMapping(value = "/get-products", method = RequestMethod.POST)
    public ResponseEntity<BasicResponse> getProducts(@RequestBody List<Integer> productIds){
        return productService.getProducts(productIds);
    }
}
