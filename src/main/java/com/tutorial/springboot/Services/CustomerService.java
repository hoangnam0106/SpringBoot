package com.tutorial.springboot.Services;

import com.tutorial.springboot.Models.Customer;
import com.tutorial.springboot.Repositories.CustomerRepository;
import com.tutorial.springboot.ResponseObject;
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

    public Customer save(Customer customer) throws Exception{
        if(customerRepository.existsByIdNo(customer.getIdNo())){
            throw new Exception("ID no existed");
        } else if(customer.getPostalCode().length() > 6){
            throw new Exception("postal code > 6");
        } else {
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

    public Optional<Customer> findById(Integer id) throws Exception{
        Optional<Customer> customer = customerRepository.findById(id);
        if(customer != null){
            throw new Exception("customer not exist");
        }else {
            return customer;
        }
    }

    public List<Customer> findCustomers(String firstName, String city){
        return customerRepository.findCustomers(firstName, city);
    }

    public List<Integer> findAllCountryID(){
        return customerRepository.findAllCountryID();
    }
}
