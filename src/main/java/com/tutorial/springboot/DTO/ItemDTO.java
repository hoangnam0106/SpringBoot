package com.tutorial.springboot.DTO;

import com.tutorial.springboot.Models.Invoice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO implements Serializable {
    private Integer id;
    private Integer invoiceId;
    private String item;
    private Integer productId;
    private int quantity;


}
