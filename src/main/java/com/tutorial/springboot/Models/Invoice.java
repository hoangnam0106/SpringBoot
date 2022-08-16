package com.example.SpringBoot.Models;


import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity

@Table(name = "invoice")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false, name = "customer_id")
    private Integer customerId;
    private Date invoiceDate;

    @ElementCollection
    private List<Item> items;

    public Invoice() {
    }

    public Invoice(Integer customerId, Date invoiceDate) {
        this.customerId = customerId;
        this.invoiceDate = invoiceDate;

    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }
}
