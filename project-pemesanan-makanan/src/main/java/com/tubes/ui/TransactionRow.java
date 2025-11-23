package com.tubes.ui;

public class TransactionRow {
    private final int orderId;
    private final String idTransaksi;
    private final String namaPemesan;
    private final int totalHarga;

    public TransactionRow(int orderId, String namaPemesan, int totalHarga) {
        this.orderId = orderId;
        this.idTransaksi = "TRX" + String.format("%03d", orderId);
        this.namaPemesan = namaPemesan != null ? namaPemesan : "Pelanggan";
        this.totalHarga = totalHarga;
    }

    public int getOrderId() { return orderId; }
    public String getIdTransaksi() { return idTransaksi; }
    public String getNamaPemesan() { return namaPemesan; }
    public int getTotalHarga() { return totalHarga; }
}