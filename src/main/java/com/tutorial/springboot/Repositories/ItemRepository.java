package com.tutorial.springboot.Repositories;

import com.tutorial.springboot.DTO.ItemDTO;
import com.tutorial.springboot.Models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
    public List<Item> findAllByInvoiceId(Integer invoiceId);
    @Modifying
    @Transactional
    @Query(value = "UPDATE item SET item.quantity = :quantity WHERE item.id = :id", nativeQuery = true)
    void updateItem(@Param("quantity") double quantity,
                    @Param("id") Integer id);
}
