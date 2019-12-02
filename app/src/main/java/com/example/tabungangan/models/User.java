package com.example.tabungangan.models;

public class User {
    private String nama,email,uuid,phoneNumber;
    private int saldo ;
     public User(String nama, String email, String uuid, int saldo,String phoneNumber) {
         this.nama = nama;
         this.email = email;
         this.uuid = uuid;
         this.saldo = saldo;
         this.phoneNumber=phoneNumber;
     }

    public String getNama() { return nama; }

    public String getEmail() {
        return email;
    }

    public String getUuid() { return uuid; }

    public int getSaldo() { return saldo; }
}
