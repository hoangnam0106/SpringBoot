package com.example.SpringBoot.Controllers;

import com.example.SpringBoot.Models.Item;
import com.example.SpringBoot.ResponseObject;
import com.example.SpringBoot.Services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping(value = "/item")
public class ItemController {
    @Autowired
    ItemService itemService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<ResponseObject> addItem(@RequestBody Item item){
        Item itemSave = itemService.addItem(item);
        if(itemSave != null){
            return new ResponseEntity<>(new ResponseObject("Add item!", itemSave), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new ResponseObject("Invoice or Product not exist", null),
                                        HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(value = "/{invoiceId}")
    public ResponseEntity<ResponseObject> findItemByInvoiceId(@PathVariable Integer invoiceId){
        return new ResponseEntity<>(new ResponseObject("successfully", itemService.findItemByInvoiceId(invoiceId)),HttpStatus.OK);
    }
}
