package com.tutorial.springboot.DTO;

import com.tutorial.springboot.Models.Customer;

public class CustomerMapper {
    private static CustomerMapper INSTANCE;

    public static CustomerMapper getINSTANCE(){
        if(INSTANCE == null){
            INSTANCE = new CustomerMapper();
        }
        return INSTANCE;
    }

    public Customer toEntity(CustomerDTO dto){
        Customer customer = new Customer();
        customer.setId(dto.getId());
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setStreet(dto.getStreet());
        customer.setPostalCode(dto.getPostalCode());
        customer.setCity(dto.getCity());
        customer.setIdNo(dto.getIdNo());
        customer.setIsActive(dto.getIsActive());
        return customer;
    }

    public CustomerDTO toDTO(Customer customer){
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setStreet(customer.getStreet());
        dto.setPostalCode(customer.getPostalCode());
        dto.setCity(customer.getCity());
        dto.setIdNo(customer.getIdNo());
        dto.setIsActive(customer.getIsActive());
        return dto;
    }
}
