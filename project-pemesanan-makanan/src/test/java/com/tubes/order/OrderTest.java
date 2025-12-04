package com.tubes.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

class OrderTest {

    @Test
    void testGetterSetter() {
        Order order = new Order();
        order.setId(101);
        order.setNamaPemesan("Budi Santoso");
        order.setTotalHarga(75000.0);
        order.setTanggalPesan("2023-12-01 10:00:00");
        order.setStatus("PAID");

        assertEquals(101, order.getId());
        assertEquals("Budi Santoso", order.getNamaPemesan());
        assertEquals(75000.0, order.getTotalHarga(), 0.001);
        assertEquals("2023-12-01 10:00:00", order.getTanggalPesan());
        assertEquals("PAID", order.getStatus());
    }

    @Test
    void testDefaultValues() {
        Order order = new Order();
        assertEquals(0, order.getId());
        assertNull(order.getNamaPemesan());
        assertEquals(0.0, order.getTotalHarga());
    }
}