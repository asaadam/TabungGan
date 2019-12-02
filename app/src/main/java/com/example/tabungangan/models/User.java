package com.example.tabungangan.models;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class User {
    private String nama="",email,uuid;
    private int saldo ;
    private static final String TAG ="USER";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user;
    private String tempNama;
    private CollectionReference userCollection = db.collection("user");
     public User(String nama, String email, String uuid, int saldo) {
         this.nama = nama;
         this.email = email;
         this.uuid = uuid;
         this.saldo = saldo;
     }

     public User (FirebaseUser user){
         this.user=user;


     }

    public String getNama() {
        userCollection.whereEqualTo("uuid",user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                nama = document.get("nama").toString();
                                Log.d(TAG,document.get("nama").toString());

                                return;
                            }
                        }
                        else{
                            Log.d(TAG,"ERROR");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG,e.toString());
                    }
                });
                return this.nama;
          }

    public String getEmail() {
        return user.getEmail();
    }

    public String getUuid() { return user.getUid(); }


}