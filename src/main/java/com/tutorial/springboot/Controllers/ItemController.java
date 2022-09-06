package com.tutorial.springboot.Controllers;

import com.tutorial.springboot.Models.Item;
import com.tutorial.springboot.Response.BasicResponse;
import com.tutorial.springboot.ResponseObject;
import com.tutorial.springboot.Services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@CrossOrigin(value = "http://localhost:4200")
@RequestMapping(value = "/api/v1/item")
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


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<BasicResponse> updateItem(@RequestParam String itemId, @RequestParam String quantity){
        return itemService.updateItem(Integer.parseInt(itemId),Double.parseDouble(quantity));
    }
}
