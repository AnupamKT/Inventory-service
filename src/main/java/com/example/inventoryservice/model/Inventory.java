package com.example.inventoryservice.model;

import com.example.inventoryservice.common.InventoryConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {

    private String productName;
    private String categoryName;
    private Long quantity;
    private String action;
}
