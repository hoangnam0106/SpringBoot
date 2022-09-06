package com.tutorial.springboot.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO{
    private Integer id;
    private String firstName;
    private String lastName;
    private String street;
    private String postalCode;
    private String city;
    private Integer idNo;
    private Integer isActive;
}
