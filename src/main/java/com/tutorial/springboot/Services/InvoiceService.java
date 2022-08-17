package com.tutorial.springboot.Services;

import com.tutorial.springboot.Models.Invoice;
import com.tutorial.springboot.Models.Item;
import com.tutorial.springboot.Repositories.CustomerRepository;
import com.tutorial.springboot.Repositories.Invoice.InvoiceRepository;
import com.tutorial.springboot.Repositories.ItemRepository;
import com.tutorial.springboot.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public class InvoiceService {
    @Autowired
    InvoiceRepository invoiceRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ItemRepository itemRepository;
    public Invoice addInvoice(Invoice invoice) throws ParseException {
        if(customerRepository.existsById(invoice.getCustomerId())){
            return invoiceRepository.save(invoice);
        }else {
            return null;
        }
    }

    public boolean existInvoice(Integer id){
        return invoiceRepository.existsById(id);
    }
    public String findCustomer(Integer id){
        return invoiceRepository.findCustomer(id);
    }

    public List<Invoice> findAllInvoice(){
        return invoiceRepository.findAll();
    }

    public ResponseEntity<ResponseObject> deleteInvoice(Integer id){
        if(invoiceRepository.existsById(id)){
            return new ResponseEntity<>(new ResponseObject(
                    "Delete invoice id = " + id, null),
                    HttpStatus.OK
            );
        }else {
            return new ResponseEntity<>(new ResponseObject(
                    "Not found invoice", null),
                    HttpStatus.NOT_FOUND
            );
        }
    }

}
