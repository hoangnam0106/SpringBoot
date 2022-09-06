package com.tutorial.springboot.Controllers;

import com.tutorial.springboot.DTO.InvoiceCreationDTO;
import com.tutorial.springboot.DTO.InvoiceDTO;
import com.tutorial.springboot.Models.Invoice;
import com.tutorial.springboot.Models.Item;
import com.tutorial.springboot.Repositories.Invoice.InvoiceRepoCustom;
import com.tutorial.springboot.Response.BasicResponse;
import com.tutorial.springboot.ResponseObject;
import com.tutorial.springboot.Services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@Controller
@CrossOrigin(value = "http://localhost:4200")
@RequestMapping(value = "/api/v1/invoice")
public class InvoiceController {
    @Autowired
    InvoiceService invoiceService;
    @Autowired
    InvoiceRepoCustom invoiceRepoCustom;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<BasicResponse> addInvoice(@RequestBody InvoiceCreationDTO dto){
        InvoiceDTO invoiceSave = null;
        try {
            invoiceSave = invoiceService.createInvoice(dto);
            BasicResponse basicResponse = new BasicResponse();
            basicResponse.setSuccess(true);
            basicResponse.setErrorCode("");
            basicResponse.setMessage("thêm thành công");
            basicResponse.setResult(invoiceSave);

            return new ResponseEntity<>(basicResponse, HttpStatus.OK);
        } catch (Exception e) {
            BasicResponse basicResponse = new BasicResponse();
            basicResponse.setSuccess(false);
            basicResponse.setErrorCode("");
            basicResponse.setMessage(e.getMessage());
            basicResponse.setResult(null);

            return new ResponseEntity<>(basicResponse, HttpStatus.OK);
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
    public ResponseEntity<ResponseObject> findAllInvoice(){
        ResponseObject responseObject = new ResponseObject();
        try {
            responseObject.setMessage("Successfully!");
            responseObject.setData(invoiceRepoCustom.findAllInvoice());
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        } catch (Exception e) {
            responseObject.setMessage(e.getMessage());
            responseObject.setData(null);
            return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{id}")
    public ResponseEntity<ResponseObject> findDetailInvoiceById(@PathVariable Integer id){
        ResponseObject responseObject = new ResponseObject();
        try {
            responseObject.setMessage("Successfully !");
            responseObject.setData(invoiceRepoCustom.findDetailInvoiceById(id));

            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        } catch (Exception e) {
            responseObject.setMessage(e.getMessage());
            responseObject.setData(null);
            return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
        }
    }
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseObject> deleteInvoice(@PathVariable Integer id){
        return invoiceService.deleteInvoice(id);
    }

    @RequestMapping(value = "/find-by-customerid", method = RequestMethod.POST)
    public ResponseEntity<BasicResponse> findByCustomerId(@RequestParam String customerId){
        return invoiceService.findInvoiceByCustomerId(Integer.parseInt(customerId));
    }

    @RequestMapping(value = "/get-items", method = RequestMethod.POST)
    public ResponseEntity<BasicResponse> getAllItems(@RequestParam String invoiceId){
        return invoiceService.getAllItem(Integer.parseInt(invoiceId));
    }
}
