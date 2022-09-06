package com.tutorial.springboot.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemDetailDTO {
    public Integer id;
    public Integer invoiceId;
    public Integer productId;
    public String item;
    public double price;
    public double vat;
    public int quantity;
}
