package com.example.inventoryservice.controller;

import com.example.inventoryservice.model.Inventory;
import com.example.inventoryservice.model.Response;
import com.example.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/update")
    public ResponseEntity<Response> updateInventory(@RequestBody Inventory inventory) throws Exception {
        Response response = inventoryService.updateInventory(inventory);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/{productName}")
    public ResponseEntity getInventoryDetails(@PathVariable String productName) {
        Response response = inventoryService.getInventoryDetails(productName);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
