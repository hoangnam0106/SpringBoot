package com.tutorial.springboot.Repositories;

import com.tutorial.springboot.Models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    public boolean existsByIdNo(int id_no);
    public Customer findByIdNo(int id_no);
    @Query(value = "SELECT country_id FROM customer", nativeQuery = true)
    public List<Integer> findAllCountryID();
    @Query(value = "SELECT * FROM customer WHERE first_name LIKE (%:firstName% or :firstName is null )" +
                    " AND city LIKE (%:city% or :city is null )",
            nativeQuery = true)
    List<Customer> findCustomers(@Param("firstName") String firstName,
                                @Param("city") String city);

}
