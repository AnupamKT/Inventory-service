package com.example.inventoryservice.service;

import com.example.inventoryservice.common.InvalidRequestException;
import com.example.inventoryservice.common.InventoryConstants;
import com.example.inventoryservice.common.InventoryServiceException;
import com.example.inventoryservice.entity.InventoryEntity;
import com.example.inventoryservice.model.Inventory;
import com.example.inventoryservice.model.InventoryDetailsResponse;
import com.example.inventoryservice.model.Response;
import com.example.inventoryservice.repository.InventoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public  synchronized Response updateInventory(Inventory inventory) throws Exception {
        String msg = null;
        Optional<InventoryEntity> optionalInventory = inventoryRepository.findByProductName(inventory.getProductName());
        if (optionalInventory.isPresent()) {
            InventoryEntity entity = optionalInventory.get();
            if (inventory.getAction().equals(InventoryConstants.InventoryAction.ADD.toString())) {
                entity.setQuantity(entity.getQuantity() + inventory.getQuantity());
            } else {
                Long newQuantity = entity.getQuantity() - inventory.getQuantity();
                if (newQuantity >= 0) {
                    entity.setQuantity(entity.getQuantity() - inventory.getQuantity());
                } else {
                    msg = "Quantity to delete is more than available quantity " + inventory.getProductName();
                    throw new InvalidRequestException(msg);
                }

            }
            updateProductQuantity(entity);
        } else {
            if (inventory.getAction().equalsIgnoreCase(InventoryConstants.InventoryAction.ADD.toString())) {
                addEntryInInventory(inventory);
            } else {
                //throw exception here
                msg = "product not available in inventory to delete" + inventory.getProductName();
                throw new InvalidRequestException(msg);
            }
        }
        msg = "inventory updated successfully";
        return new Response(200, msg);
    }

    private void addEntryInInventory(Inventory inventory) throws InventoryServiceException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            InventoryEntity entity = mapper.convertValue(inventory, InventoryEntity.class);
            entity.setCreatedDate(new Date());
            entity.setUpdatedDate(new Date());
            inventoryRepository.save(entity);
        } catch (Exception e) {
            String msg = "server error occurred while adding entry in inventory" + e.getMessage();
            throw new InventoryServiceException(msg);
        }

    }

    private void updateProductQuantity(InventoryEntity entity) throws InventoryServiceException {
        try {
            if (entity.getQuantity() > 0) {
                //setting new updated date
                entity.setUpdatedDate(new Date());
                inventoryRepository.save(entity);
            } else {
                inventoryRepository.delete(entity);
            }
        } catch (Exception e) {
            String msg = "server error occurred while updating inventory" + e.getMessage();
            throw new InventoryServiceException(msg);
        }
    }

    public Response getInventoryDetails(String productName) {
        InventoryDetailsResponse inventoryDetails = new InventoryDetailsResponse();
        Long notAvailable = 0L;
        Optional<InventoryEntity> inventoryEntity = inventoryRepository.findByProductName(productName);
        if (inventoryEntity.isPresent()) {
            InventoryEntity entity = inventoryEntity.get();
            inventoryDetails.setProductName(entity.getProductName());
            inventoryDetails.setQuantity(entity.getQuantity());
        } else {
            inventoryDetails.setProductName(productName);
            inventoryDetails.setQuantity(notAvailable);
        }
        return new Response(200, inventoryDetails);
    }
}
