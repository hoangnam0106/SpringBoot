package com.tutorial.springboot.Repositories;

import com.tutorial.springboot.DTO.CustomerDTO;
import com.tutorial.springboot.Models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
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

    @Modifying
    @Transactional
    @Query(value = "UPDATE customer SET is_active = 0 WHERE id = :id", nativeQuery = true)
    void deleteCustomer(@Param("id") Integer id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE customer SET is_active = 1 WHERE id_no = :idNo", nativeQuery = true)
    void activeCustomer(@Param("idNo") Integer idNo);
    @Query(value = "SELECT customer.is_active FROM customer WHERE id_no = :idNo", nativeQuery = true)
    Integer getActive(@Param("idNo") Integer idNo);


    @Query(value =  "SELECT * FROM customer " +
            "WHERE (city LIKE :city OR :city IS NULL) " +
            "AND (first_name LIKE :firstName OR :firstName IS NULL) " +
            "AND (last_name LIKE :lastName OR :lastName IS NULL) " +
            "AND (street LIKE :street OR :street IS NULL) " +
            "AND (postal_code LIKE :postalCode OR :postalCode IS NULL) " +
            "AND (id_no LIKE :idNo OR :idNo IS NULL) " +
            "AND is_active = 1 ", nativeQuery = true)
    List<Customer> searchCustomers(@Param("city") String city,
                           @Param("firstName") String firstName,
                           @Param("lastName") String lastName,
                           @Param("street") String street,
                           @Param("postalCode") String postalCode,
                           @Param("idNo") String idNo);
}
