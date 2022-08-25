package com.tutorial.springboot.DTO;

import com.tutorial.springboot.Models.Item;

public class ItemMapper {

    public static ItemMapper INSTANCE;

    public static ItemMapper getINSTANCE(){
        if(INSTANCE == null){
            INSTANCE = new ItemMapper();
        }

        return INSTANCE;
    }

    public Item toEntity(ItemDTO itemDTO){
        Item item = new Item();

        item.setId(itemDTO.getId());
        item.setItem(itemDTO.getItem());
        item.setInvoiceId(itemDTO.getInvoiceId());
        item.setQuantity(itemDTO.getQuantity());
        item.setProductId(itemDTO.getProductId());


        return item;
    }

    public ItemDTO toItemDTO(Item item){
        ItemDTO itemDTO = new ItemDTO();

        itemDTO.setId(item.getId());
        itemDTO.setItem(item.getItem());
        itemDTO.setInvoiceId(item.getInvoiceId());
        itemDTO.setQuantity(item.getQuantity());
        itemDTO.setProductId(item.getProductId());

        return itemDTO;
    }
}
