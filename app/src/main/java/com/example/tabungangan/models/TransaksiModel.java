package com.example.tabungangan.models;

public class TransaksiModel {
    private String tanggal;
    private String bulan;
    private String tahun;
    private String tipe;
    private String kategori;
    private String jumlah;

    public TransaksiModel() {}


    public TransaksiModel(String tanggal, String bulan, String tahun, String tipe, String kategori, String jumlah) {
        this.tanggal = tanggal;
        this.bulan = bulan;
        this.tahun = tahun;
        this.tipe = tipe;
        this.kategori = kategori;
        this.jumlah = jumlah;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getBulan() {
        return bulan;
    }

    public String getTahun() {
        return tahun;
    }

    public String getTipe() {
        return tipe;
    }

    public String getKategori() {
        return kategori;
    }

    public String getJumlah() {
        return jumlah;
    }
}
