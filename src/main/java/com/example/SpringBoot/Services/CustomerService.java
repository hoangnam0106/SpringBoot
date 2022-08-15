package com.example.SpringBoot.Services;

import com.example.SpringBoot.Models.Customer;
import com.example.SpringBoot.Repositories.CustomerRepository;
import com.example.SpringBoot.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public Customer save(Customer customer){
        if(customerRepository.existsByIdNo(customer.getIdNo())){
            return null;
        }else {
            return customerRepository.save(customer);
        }
    }

    public Customer update(Customer customer, Integer id){
        Optional<Customer> customerFound = customerRepository.findById(id);
        customerFound.ifPresent(customer1 -> {
              customer1.setFirstName(customer.getFirstName());
              customer1.setLastName(customer.getLastName());
              customer1.setCity(customer.getCity());
              customer1.setPostalCode(customer.getPostalCode());
              customer1.setCountryId(customer.getCountryId());
              customer1.setIdNo(customer.getIdNo());

              customerRepository.save(customer1);
        });
        return customer;
    }

    public ResponseEntity<ResponseObject> deteleCustomer(Integer id){
        if(customerRepository.findById(id).isPresent()){
            customerRepository.deleteById(id);
            return new ResponseEntity<>(new ResponseObject(
                    "Delete customer id = " + id, customerRepository.findById(id)),
                    HttpStatus.OK
            );
        }else {
            return new ResponseEntity<>(new ResponseObject(
                    "Not found customer id = " + id,null), HttpStatus.NOT_FOUND
            );
        }
    }

    public Customer findById(Integer id){
        return customerRepository.findById(id).orElse(null);
    }

    public List<Customer> findCustomers(String firstName, String city){
        return customerRepository.findCustomers(firstName, city);
    }

    public List<Integer> findAllCountryID(){
        return customerRepository.findAllCountryID();
    }
}
