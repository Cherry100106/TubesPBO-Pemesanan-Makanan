package com.tubes.ui;

public class TransactionRow {
    private String idTransaksi;
    private String namaPemesan;
    private int totalHarga;

    public TransactionRow(String idTransaksi, String namaPemesan, int totalHarga) {
        this.idTransaksi = idTransaksi;
        this.namaPemesan = namaPemesan;
        this.totalHarga = totalHarga;
    }

    public String getIdTransaksi() { return idTransaksi; }
    public String getNamaPemesan() { return namaPemesan; }
    public int getTotalHarga() { return totalHarga; }
}
