package com.tutorial.springboot.Services;
import java.util.ArrayList;

import com.tutorial.springboot.DTO.*;
import com.tutorial.springboot.Models.Invoice;
import com.tutorial.springboot.Models.Item;
import com.tutorial.springboot.Models.Product;
import com.tutorial.springboot.Repositories.CustomerRepository;
import com.tutorial.springboot.Repositories.Invoice.InvoiceRepository;
import com.tutorial.springboot.Repositories.ItemRepository;
import com.tutorial.springboot.Repositories.ProductRepository;
import com.tutorial.springboot.Response.BasicResponse;
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

    public ResponseEntity<BasicResponse> findInvoiceByCustomerId(Integer customerId){
        List<Invoice> invoices = invoiceRepository.findInvoiceByCustomerId(customerId);
        BasicResponse basicResponse = new BasicResponse();
        basicResponse.setSuccess(false);
        basicResponse.setErrorCode("");
        basicResponse.setMessage("");
        basicResponse.setResult(null);

        if(!invoices.isEmpty()){
            List<InvoiceResponseDTO> invoiceResponseDTOs = new ArrayList<>();
            for (Invoice invoice: invoices) {
                InvoiceResponseDTO invoiceResponseDTO = new InvoiceResponseDTO();
                invoiceResponseDTO.setId(invoice.getId());
                invoiceResponseDTO.setInvoiceDate(invoice.getInvoiceDate());
                List<Item> items = itemRepository.findAllByInvoiceId(invoice.getId());
                if(!items.isEmpty()){
                    double total = 0L;
                    for (Item item: items) {
                        Product product = productRepository.findById(item.getProductId()).orElse(null);
                        total += item.getQuantity() * product.getPrice();
                    }
                    invoiceResponseDTO.setTotal(total);
                }else {
                    invoiceResponseDTO.setTotal(0);
                }

                invoiceResponseDTOs.add(invoiceResponseDTO);
            }

            basicResponse.setSuccess(true);
            basicResponse.setErrorCode("");
            basicResponse.setMessage("");
            basicResponse.setResult(invoiceResponseDTOs);

            return new ResponseEntity<>(basicResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }

    public ResponseEntity<BasicResponse> getAllItem(Integer invoiceId){
        List<Item> items = itemRepository.findAllByInvoiceId(invoiceId);
        BasicResponse basicResponse = new BasicResponse();
        basicResponse.setSuccess(false);
        basicResponse.setErrorCode("");
        basicResponse.setResult(null);
        if(!items.isEmpty()){
            List<ItemDetailDTO> itemDetailDTOS = new ArrayList<>();
            for (Item item:items) {
                ItemDetailDTO itemDetailDTO = new ItemDetailDTO();
                itemDetailDTO.setId(item.getId());
                itemDetailDTO.setInvoiceId(item.getInvoiceId());
                itemDetailDTO.setItem(item.getItem());
                itemDetailDTO.setProductId(item.getProductId());
                Product product = productRepository.findById(item.getProductId()).orElse(null);
                itemDetailDTO.setPrice(product.getPrice());
                itemDetailDTO.setVat(product.getVat());
                itemDetailDTO.setQuantity(item.getQuantity());

                itemDetailDTOS.add(itemDetailDTO);
            }
            basicResponse.setSuccess(true);
            basicResponse.setMessage("OK");
            basicResponse.setResult(itemDetailDTOS);

            return new ResponseEntity<>(basicResponse,HttpStatus.OK);
        }
        basicResponse.setMessage("empty");
        return new ResponseEntity<>(basicResponse,HttpStatus.OK);
    }
}
