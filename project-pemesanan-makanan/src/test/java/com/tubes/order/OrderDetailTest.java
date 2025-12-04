package com.tubes.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;


class OrderDetailTest {

    @Test
    void testGetterSetter() {
        OrderDetail detail = new OrderDetail();
        detail.setNamaMenu("Nasi Goreng");
        detail.setJumlah(2);
        detail.setSubtotal(30000.0);

        assertEquals("Nasi Goreng", detail.getNamaMenu());
        assertEquals(2, detail.getJumlah());
        assertEquals(30000.0, detail.getSubtotal(), 0.001);
    }

    @Test
    void testInitialState() {
        OrderDetail detail = new OrderDetail();
        assertNull(detail.getNamaMenu());
        assertEquals(0, detail.getJumlah());
        assertEquals(0.0, detail.getSubtotal());
    }
}