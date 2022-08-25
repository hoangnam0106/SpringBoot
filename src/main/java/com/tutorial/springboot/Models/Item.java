package com.tutorial.springboot.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer invoiceId;
    private String item;
    private Integer productId;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "invoiceId", insertable = false, updatable = false)
    private Invoice invoice;

}
