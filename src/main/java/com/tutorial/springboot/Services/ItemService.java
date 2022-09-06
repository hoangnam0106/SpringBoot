package com.tutorial.springboot.Services;

import com.tutorial.springboot.Models.Item;
import com.tutorial.springboot.Repositories.Invoice.InvoiceRepository;
import com.tutorial.springboot.Repositories.ItemRepository;
import com.tutorial.springboot.Repositories.ProductRepository;
import com.tutorial.springboot.Response.BasicResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    public ResponseEntity<BasicResponse> updateItem(Integer itemId, double quantity){
        BasicResponse basicResponse = new BasicResponse();
        basicResponse.setSuccess(false);
        basicResponse.setErrorCode("");
        basicResponse.setMessage("");
        basicResponse.setResult(null);

        if(itemRepository.existsById(itemId)){
            itemRepository.updateItem(quantity,itemId);
            basicResponse.setSuccess(true);
            basicResponse.setMessage("update thành công");
            return new ResponseEntity<>(basicResponse, HttpStatus.OK);
        }

        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }

}
