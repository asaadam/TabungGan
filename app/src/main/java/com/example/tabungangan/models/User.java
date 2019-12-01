package com.example.tabungangan.models;

import com.google.firebase.auth.FirebaseUser;

public class User {
    private String nama,email,uuid;
    private int saldo ;
   private FirebaseUser user;

     public User(String nama, String email, String uuid, int saldo) {
         this.nama = nama;
         this.email = email;
         this.uuid = uuid;
         this.saldo = saldo;
     }

     public User (FirebaseUser user){
         this.user=user;
     }

    public String getNama() { return user.getDisplayName(); }

    public String getEmail() {
        return user.getEmail();
    }

    public String getUuid() { return user.getUid(); }


}