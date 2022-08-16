package com.example.SpringBoot.Repositories.Invoice;

import com.example.SpringBoot.Models.Invoice;

import java.util.List;

public interface InvoiceRepoCustom {
    public List<Invoice> findAllInvoice() throws Exception;
    public Invoice findDetailInvoiceById(Integer id) throws Exception;
}
