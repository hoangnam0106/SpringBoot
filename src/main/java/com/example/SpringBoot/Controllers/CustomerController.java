package com.example.SpringBoot.Controllers;

import com.example.SpringBoot.Models.Customer;
import com.example.SpringBoot.ResponseObject;
import com.example.SpringBoot.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


@Controller
@RequestMapping(value = "/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<ResponseObject> save(@RequestBody Customer customer){
        Customer customerSave = customerService.save(customer);
        if(customerSave != null){
            return new ResponseEntity<>(new ResponseObject("Save customer!", customerSave), HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(new ResponseObject("exist Id No", null), HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Customer> update(@PathVariable Integer id, @RequestBody Customer customer){
        return new ResponseEntity<>(customerService.update(customer, id), HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseObject> delete(@PathVariable Integer id){
        return customerService.deteleCustomer(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ResponseObject> find(@PathVariable Integer id){
        Customer customer = customerService.findById(id);
        if(customer != null){
            return new ResponseEntity<>(new ResponseObject(
                    "successful", customer),
                    HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new ResponseObject(
                    "Not found customer id = " + id, null),
                    HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/country", method = RequestMethod.GET)
    public ResponseEntity<List<Integer>> findAllCountry(){
        List<Integer> countryFound = customerService.findAllCountryID();
        return new ResponseEntity<>(countryFound, HttpStatus.OK);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<ResponseObject> findCustomers(@RequestBody Customer customer){
        List<Customer> customers = customerService.findCustomers(customer.getFirstName(),
                                                                 customer.getCity());
        if(customers.isEmpty()){
            return new ResponseEntity<>(new ResponseObject(
               "Not found customer",null),
               HttpStatus.NOT_FOUND
            );
        }else {
            return new ResponseEntity<>(new ResponseObject(
                "Successful", customers),
                HttpStatus.OK
            );
        }

    }
}
