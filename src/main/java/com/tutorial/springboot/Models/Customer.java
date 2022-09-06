package com.tutorial.springboot.Models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String firstName;

    private String lastName;
    private String street;
    private String postalCode;
    private String city;

    @Column(nullable = false)
    private Integer idNo;
    private Integer isActive;

}
