package com.tutorial.springboot.DTO;

import com.tutorial.springboot.Models.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceCreationDTO implements Serializable {
    private Integer id;
    private Integer customerId;
    private Date invoiceDate;

    List<Item> items;
}
