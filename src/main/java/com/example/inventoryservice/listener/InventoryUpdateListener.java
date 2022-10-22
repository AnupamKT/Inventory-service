package com.example.inventoryservice.listener;

import com.example.inventoryservice.model.Inventory;
import com.example.inventoryservice.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "kafkaInventoryUpdate",
        groupId = "inventoryUpdate",
        containerFactory = "getKafkaConsumerFactory")
@Slf4j
public class InventoryUpdateListener {
    @Autowired
    private InventoryService service;

    @KafkaHandler
    public void updateInventory(Inventory inventory){
        try {
            service.updateInventory(inventory);
        } catch (Exception e) {
            log.error("error occurred in InventoryUpdateListener while updating inventory for "+inventory
                    +e.getMessage());
        }
    }
}
