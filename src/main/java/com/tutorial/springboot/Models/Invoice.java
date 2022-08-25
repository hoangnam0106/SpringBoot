package com.tutorial.springboot.Models;


import com.tutorial.springboot.DTO.InvoiceDTO;
import com.tutorial.springboot.DTO.ItemMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "invoice")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false, name = "customer_id")
    private Integer customerId;
    private Date invoiceDate;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private List<Item> items;

}
