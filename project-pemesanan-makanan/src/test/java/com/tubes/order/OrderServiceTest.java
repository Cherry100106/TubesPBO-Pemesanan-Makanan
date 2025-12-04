package com.tubes.order;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tubes.cart.CartItem;

class OrderServiceTest {

    private OrderService service;

    @BeforeEach
    void setup() {
        service = new OrderService();
    }

    @Test
    void saveOrder_nullItems_throwsException() {
        // Test Case: Keranjang NULL harus melempar error
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.saveOrder(null, "Pembeli A");
        });

        assertTrue(exception.getMessage().contains("Keranjang tidak boleh kosong"));
    }

    @Test
    void saveOrder_emptyItems_throwsException() {
        // Test Case: Keranjang KOSONG (List dibuat tapi isi 0) harus melempar error
        List<CartItem> emptyList = new ArrayList<>();
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.saveOrder(emptyList, "Pembeli B");
        });

        assertTrue(exception.getMessage().contains("Keranjang tidak boleh kosong"));
    }

    @Test
    void saveOrder_emptyName_throwsException() {
        // Test Case: Item ada, tapi Nama Kosong
        List<CartItem> dummyList = new ArrayList<>();
        dummyList.add(null); 

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.saveOrder(dummyList, ""); // Nama kosong string
        });

        assertTrue(exception.getMessage().contains("Nama pelanggan wajib diisi"));
    }
    
    @Test
    void saveOrder_whitespaceName_throwsException() {
        // Test Case: Nama isinya spasi doang
        List<CartItem> dummyList = new ArrayList<>();
        dummyList.add(null); 

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.saveOrder(dummyList, "   "); // Spasi saja
        });

        assertTrue(exception.getMessage().contains("Nama pelanggan wajib diisi"));
    }
}