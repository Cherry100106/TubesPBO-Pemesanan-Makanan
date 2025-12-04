package com.tubes.menu;

public class MenuItem {
    private final int id;
    private final String namaMakanan;
    private final String kategori;
    private final double harga;
    private final boolean isAvailable;

    public MenuItem(int id, String namaMakanan, String kategori, double harga, boolean isAvailable) {
        this.id = id;
        this.namaMakanan = namaMakanan;
        this.kategori = kategori;
        this.harga = harga;
        this.isAvailable = isAvailable;
    }

    public int getId() {
        return id;
    }

    public String getNamaMakanan() {
        return namaMakanan;
    }

    public String getKategori() {
        return kategori;
    }

    public double getHarga() {
        return harga;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
}
