package com.tutorial.springboot.Services;

import com.tutorial.springboot.DTO.InvoiceCreationDTO;
import com.tutorial.springboot.DTO.InvoiceDTO;
import com.tutorial.springboot.DTO.InvoiceMapper;
import com.tutorial.springboot.Models.Invoice;
import com.tutorial.springboot.Models.Item;
import com.tutorial.springboot.Repositories.CustomerRepository;
import com.tutorial.springboot.Repositories.Invoice.InvoiceRepository;
import com.tutorial.springboot.Repositories.ItemRepository;
import com.tutorial.springboot.Repositories.ProductRepository;
import com.tutorial.springboot.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class InvoiceService {
    @Autowired
    InvoiceRepository invoiceRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ProductRepository productRepository;
    public InvoiceDTO createInvoice(InvoiceCreationDTO dto) throws Exception {
        Invoice invoice = InvoiceMapper.getINSTANCE().toEntity(dto);
        if(customerRepository.existsById(invoice.getCustomerId())){
            Invoice invoice1 = invoiceRepository.save(invoice);
            List<Item> itemsSave = dto.getItems();
            for (Item item: itemsSave) {
                item.setInvoiceId(invoice1.getId());
                if(!productRepository.existsById(item.getProductId())){
                    throw new Exception("product not exist !");
                }
            }
            List<Item> items = itemRepository.saveAll(itemsSave);
            invoice.setItems(items);
            return InvoiceMapper.getINSTANCE().toInvoiceDTO(invoice);
        }else {
            throw new Exception("customer not exist !");
        }
    }

    public String findCustomer(Integer id){
        return invoiceRepository.findCustomer(id);
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
