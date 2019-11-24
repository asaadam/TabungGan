package com.example.tabungangan;
public class User {
    String nama,email,uuid;
    int saldo ;

     public User(String nama, String email, String uuid, int saldo) {
         this.nama = nama;
         this.email = email;
         this.uuid = uuid;
         this.saldo = saldo;
     }

    public String getNama() { return nama; }

    public String getEmail() {
        return email;
    }

    public String getUuid() { return uuid; }

    public int getSaldo() {
        return saldo;
    }
}