package com.example.SpringBoot.Controllers;

import com.example.SpringBoot.Models.Invoice;
import com.example.SpringBoot.ResponseObject;
import com.example.SpringBoot.Services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping(value = "/invoice")
public class InvoiceController {
    @Autowired
    InvoiceService invoiceService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<ResponseObject> addInvoice(@RequestBody Invoice invoice) throws ParseException {
        Invoice invoiceSave = invoiceService.addInvoice(invoice);
        if(invoiceSave != null){
            return new ResponseEntity<>(new ResponseObject(
                    "Successful", invoiceSave),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(new ResponseObject(
                    "customer not exist", null),
                    HttpStatus.NOT_FOUND
            );
        }

    }

    @RequestMapping(value = "/search-customer/{id}", method = RequestMethod.GET)
    public ResponseEntity<ResponseObject> findCustomer(@PathVariable Integer id){
        String customer = invoiceService.findCustomer(id);
        if(customer != null){
            return new ResponseEntity<>(new ResponseObject(
                    "Successful", customer),
                    HttpStatus.OK
            );
        }else {
            return new ResponseEntity<>(new ResponseObject(
                    "Not found customer", null),
                    HttpStatus.NOT_FOUND
            );
        }

    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<Invoice>> findAllInvoice(){
        return new ResponseEntity<>(invoiceService.findAllInvoice(), HttpStatus.OK);
    }

//    @RequestMapping(value = "/item/{id}", method = RequestMethod.GET)
//    public ResponseEntity<ResponseObject> findAllItems(@PathVariable Integer id){
//        if(invoiceService.existInvoice(id)){
//            List<Item> items = invoiceService.findAllItem(id);
//            if(items.isEmpty()){
//                return new ResponseEntity<>(new ResponseObject(
//                        "invoice is empty", null),
//                        HttpStatus.NOT_FOUND
//                );
//            }else{
//                return new ResponseEntity<>(new ResponseObject(
//                        "Successful", items),
//                        HttpStatus.NOT_FOUND
//                );
//            }
//        }else {
//            return new ResponseEntity<>(new ResponseObject(
//                    "Not found invoice", null),
//                    HttpStatus.NOT_FOUND
//            );
//        }
//    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseObject> deleteInvoice(@PathVariable Integer id){
        return invoiceService.deleteInvoice(id);
    }
}
