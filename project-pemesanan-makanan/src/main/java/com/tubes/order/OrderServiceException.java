package com.tubes.order;

/**
 * Custom exception untuk OrderService.
 * Digunakan untuk membedakan error dari order processing dari error lainnya.
 */
public class OrderServiceException extends Exception {
    public OrderServiceException(String message) {
        super(message);
    }

    public OrderServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
