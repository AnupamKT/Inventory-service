package com.example.inventoryservice.common;

import com.example.inventoryservice.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class InventoryServiceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = InvalidRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleInvalidRequestException(InvalidRequestException ex){
        ErrorResponse error= new ErrorResponse(400,ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(value = InventoryServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity handleInventoryServiceException(InventoryServiceException ex){
        ErrorResponse error= new ErrorResponse(500,ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }
}
