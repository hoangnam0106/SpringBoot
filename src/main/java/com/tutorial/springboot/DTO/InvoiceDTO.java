package com.tutorial.springboot.DTO;

import com.tutorial.springboot.Models.Invoice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO implements Serializable {
    private Integer id;
    private Integer customerId;
    private Date invoiceDate;
    private List<ItemDTO> itemDTOs;

}
