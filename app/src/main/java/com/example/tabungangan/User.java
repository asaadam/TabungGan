package com.example.tabungangan;
public class User {
    String nama,email,uuid;
    int saldo ;

     public User(String nama, String uuid, int saldo) {
         this.nama = nama;
         this.uuid = uuid;
         this.saldo = saldo;
     }

    public String getEmail() {
        return email;
    }

    public int getSaldo() {
        return saldo;
    }

}