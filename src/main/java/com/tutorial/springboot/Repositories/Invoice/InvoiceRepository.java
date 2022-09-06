package com.tutorial.springboot.Repositories.Invoice;

import com.tutorial.springboot.Models.Invoice;
import com.tutorial.springboot.Models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    @Query(value = "SELECT customer.first_name FROM customer " +
                    "INNER JOIN invoice ON customer.id = invoice.customer_id " +
                    "WHERE invoice.id = ?1",
                    nativeQuery = true)
    public String findCustomer(Integer id);

    List<Invoice> findInvoiceByCustomerId(Integer customerId);

}
