package com.example.inventoryservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="INVENTORY_DETAILS")
@JsonIgnoreProperties(ignoreUnknown = true)
public class InventoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID inventoryId;
    private String productName;
    private String categoryName;
    private Long quantity;
    private Date createdDate;
    private Date updatedDate;
}
