package com.example.tabungangan.models;

import android.text.Editable;

public class WishlistModel {
    private  String tanggalMulai,tanggalAkhir,wish,uang,uuid;

    public WishlistModel() {}
    
    public WishlistModel(String uuid, String tanggalMulai, String tanggalAkhir, String wish, String uang) {
        this.tanggalMulai = tanggalMulai;
        this.tanggalAkhir = tanggalAkhir;
        this.wish = wish;
        this.uang = uang;
        this.uuid = uuid;
    }

    public String getTanggalMulai() {
        return tanggalMulai;
    }

    public String getTanggalAkhir() { return tanggalAkhir; }

    public String getWish() {
        return wish;
    }

    public String getUang() {
        return uang;
    }

    public String getUuid() { return uuid; }

    public void setUang(String uang) {
        this.uang = uang;
    }
}

