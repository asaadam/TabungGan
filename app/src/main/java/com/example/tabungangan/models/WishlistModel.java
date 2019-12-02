package com.example.tabungangan.models;

import android.text.Editable;

public class WishlistModel {
    private  String tanggalMulai,tanggalAkhir,wish,uang;

    public WishlistModel(String tanggalMulai, String tanggalAkhir, String wish, String uang) {
        this.tanggalMulai = tanggalMulai;
        this.tanggalAkhir = tanggalAkhir;
        this.wish = wish;
        this.uang = uang;
    }

    public String getTanggalMulai() {
        return tanggalMulai;
    }

    public String getTanggalAkhir() {
        return tanggalAkhir;
    }

    public String getWish() {
        return wish;
    }

    public String getUang() {
        return uang;
    }
}

