package com.example.SpringBoot.Repositories.Invoice;

import com.example.SpringBoot.Models.Invoice;
import com.example.SpringBoot.Models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    @Query(value = "SELECT customer.first_name FROM customer " +
                    "INNER JOIN invoice ON customer.id = invoice.customer_id " +
                    "WHERE invoice.id = ?1",
                    nativeQuery = true)
    public String findCustomer(Integer id);

    @Query(value =  " SELECT item.invoice_id, item.item, item.product_id, item.quantity " +
                    " FROM item " +
                    " INNER JOIN invoice ON invoice.id = item.invoice_id" +
                    " WHERE invoice.id = ?1", nativeQuery = true)
    public List<Item> findAllItem(Integer id);


}
