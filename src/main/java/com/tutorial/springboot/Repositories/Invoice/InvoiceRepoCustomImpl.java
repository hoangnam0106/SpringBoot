package com.example.SpringBoot.Repositories.Invoice;

import com.example.SpringBoot.Models.Invoice;
import com.example.SpringBoot.Models.Item;
import com.example.SpringBoot.Repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Repository
public class InvoiceRepoCustomImpl implements InvoiceRepoCustom{
    @Autowired
    ItemRepository itemRepository;

    InvoiceRepository invoiceRepository;
    @PersistenceContext
    EntityManager em;
    @Override
    public List<Invoice> findAllInvoice() throws Exception{
        String sql = " SELECT invoice.id, invoice.customer_id, invoice.invoice_date FROM `invoice`";
        Query query = em.createNativeQuery(sql);

        List<Object[]> results = query.getResultList();
        List<Invoice> invoices = new ArrayList<>();
        if(Objects.nonNull(results) && results.size() > 0){
            results.forEach(item -> {
                Invoice invoice = new Invoice();
                List<Item> items = itemRepository.findAllByInvoiceId(
                        item[0] != null ? Integer.valueOf(item[0].toString()) : null
                );
                invoice.setItems(items);
                invoice.setId(item[0] != null ? Integer.valueOf(item[0].toString()) : null);
                invoice.setCustomerId(item[1] != null ? Integer.valueOf(item[1].toString()) : null);
                invoice.setInvoiceDate(item[2] != null ? (Date) item[2] : null);
                invoices.add(invoice);
            });
            return invoices;
        }else {
            throw new Exception("empty!");
        }

    }

    @Override
    public Invoice findDetailInvoiceById(Integer id) throws Exception {
        String sql = " SELECT invoice.id, invoice.customer_id, invoice.invoice_date " +
                     " FROM `invoice` WHERE invoice.id = :id";
        Query query = em.createNativeQuery(sql);
        query.setParameter("id", id);
        Object[] result = (Object[]) query.getSingleResult();
        Invoice invoice = new Invoice();
        if(Objects.nonNull(result)){
                List<Item> items = itemRepository.findAllByInvoiceId(
                        result[0] != null ? Integer.valueOf(result[0].toString()) : null
                );
                invoice.setItems(items);
                invoice.setId(result[0] != null ? Integer.valueOf(result[0].toString()) : null);
                invoice.setCustomerId(result[1] != null ? Integer.valueOf(result[1].toString()) : null);
                invoice.setInvoiceDate(result[2] != null ? (Date) result[2] : null);

            return invoice;
        }else {
            throw new Exception("Invoice not exist !");
        }
    }
}
