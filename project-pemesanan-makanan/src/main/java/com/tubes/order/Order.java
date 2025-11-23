package com.tubes.order;

public class Order {
    private int id;
    private String namaPemesan;
    private double totalHarga;
    private String tanggalPesan;
    private String status;

    public Order() {
        // Diperlukan untuk kompatibilitas (misal: saat mapping dari ResultSet)
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNamaPemesan() { return namaPemesan; }
    public void setNamaPemesan(String namaPemesan) { this.namaPemesan = namaPemesan; }

    public double getTotalHarga() { return totalHarga; }
    public void setTotalHarga(double totalHarga) { this.totalHarga = totalHarga; }

    public String getTanggalPesan() { return tanggalPesan; }
    public void setTanggalPesan(String tanggalPesan) { this.tanggalPesan = tanggalPesan; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}