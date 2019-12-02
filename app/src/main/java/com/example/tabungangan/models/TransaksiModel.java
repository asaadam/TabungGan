package com.example.tabungangan.models;

public class TransaksiModel {
    private String hari;
    private String tanggal;
    private String bulan;
    private String tahun;
    private String tipe;
    private String kategori;
    private String jumlah;
    private String uuid;

    public TransaksiModel() {}

    public TransaksiModel(String hari, String tanggal, String bulan, String tahun, String tipe, String kategori, String jumlah, String uuid) {
        this.hari = hari;
        this.tanggal = tanggal;
        this.bulan = bulan;
        this.tahun = tahun;
        this.tipe = tipe;
        this.kategori = kategori;
        this.jumlah = jumlah;
        this.uuid = uuid;
    }

    public String getHari() {
        return hari;
    }

    public String getTanggal() { return tanggal; }

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

    public String getUuid() { return uuid; }
}
