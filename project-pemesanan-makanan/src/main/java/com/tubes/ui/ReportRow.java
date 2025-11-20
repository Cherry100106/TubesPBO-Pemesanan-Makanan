package com.tubes.ui;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ReportRow {

    private final StringProperty tanggal;
    private final IntegerProperty jumlahOrder;
    private final IntegerProperty pendapatan;

    public ReportRow(String tanggal, int jumlahOrder, int pendapatan) {
        this.tanggal = new SimpleStringProperty(tanggal);
        this.jumlahOrder = new SimpleIntegerProperty(jumlahOrder);
        this.pendapatan = new SimpleIntegerProperty(pendapatan);
    }

    public StringProperty tanggalProperty() { return tanggal; }
    public IntegerProperty jumlahOrderProperty() { return jumlahOrder; }
    public IntegerProperty pendapatanProperty() { return pendapatan; }
}
