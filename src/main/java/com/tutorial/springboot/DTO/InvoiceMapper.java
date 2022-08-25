package com.tutorial.springboot.DTO;

import com.tutorial.springboot.Models.Invoice;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.stream.Collectors;


public class InvoiceMapper {
    public static InvoiceMapper INSTANCE;

    public static InvoiceMapper getINSTANCE(){
        if(INSTANCE == null){
            INSTANCE = new InvoiceMapper();
        }

        return INSTANCE;
    }
    public Invoice toEntity(InvoiceCreationDTO dto){
        Invoice invoice = new Invoice();
        invoice.setId(dto.getId());
        invoice.setInvoiceDate(dto.getInvoiceDate());
        invoice.setCustomerId(dto.getCustomerId());
        return invoice;
    }

    public InvoiceDTO toInvoiceDTO(Invoice invoice){
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setId(invoice.getId());
        invoiceDTO.setInvoiceDate(invoice.getInvoiceDate());
        invoiceDTO.setCustomerId(invoice.getCustomerId());
        invoiceDTO.setItemDTOs( invoice.getItems().stream()
                                .map(item -> ItemMapper.getINSTANCE().toItemDTO(item))
                                .collect(Collectors.toList()));

        return invoiceDTO;
    }
}
