package com.example.SpringBoot.Repositories;

import com.example.SpringBoot.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query(value = "SELECT * FROM `product` WHERE name LIKE %?1%", nativeQuery = true)
    public List<Product> _findByName(String productName);

    public boolean existsByName(String productName);
}
