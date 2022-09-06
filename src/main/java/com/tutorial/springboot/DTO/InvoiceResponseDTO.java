package com.tutorial.springboot.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class InvoiceResponseDTO {
    public Integer id;
    public Date invoiceDate;
    public double total;

}
