package com.tutorial.springboot.Services;

import com.tutorial.springboot.Models.Item;
import com.tutorial.springboot.Repositories.Invoice.InvoiceRepository;
import com.tutorial.springboot.Repositories.ItemRepository;
import com.tutorial.springboot.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ItemService {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    InvoiceRepository invoiceRepository;
    @Autowired
    ProductRepository productRepository;

    public Item addItem(Item item){
        if(invoiceRepository.existsById(item.getInvoiceId())
                && productRepository.existsById(item.getProductId())){
            return itemRepository.save(item);
        } else {
            return null;
        }
    }

    public List<Item> findItemByInvoiceId(Integer invoiceId){
        return itemRepository.findAllByInvoiceId(invoiceId);
    }

}
