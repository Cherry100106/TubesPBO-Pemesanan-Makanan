package com.tubes.factory;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.tubes.order.Order;

public class OrderFactory {

    private OrderFactory() {

    }
    
    public static Order createFromResultSet(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getInt("id"));
        order.setNamaPemesan(rs.getString("nama_pelanggan"));
        order.setTotalHarga(rs.getDouble("total_harga"));
        order.setTanggalPesan(rs.getTimestamp("tanggal_pesan").toString());
        order.setStatus(rs.getString("status"));
        return order;
    }
}