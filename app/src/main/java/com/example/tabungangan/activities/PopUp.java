package com.example.tabungangan.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tabungangan.R;
import com.example.tabungangan.models.WishlistModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PopUp extends AppCompatActivity implements View.OnClickListener {
    Button submit;
    private EditText Pengurang;
    private int jumlahKurang;
    private String tempUang;
    String uangSisa, uangTambah,namawish;
    private FirebaseAuth auth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference wishlistRef = db.collection("wishlist");

    int dataPengurang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);
        Pengurang = findViewById(R.id.inputPengurang);

        auth = FirebaseAuth.getInstance();

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            uangSisa = extras.getString("uang");
            namawish = extras.getString("nama");
        }

//        Toast.makeText(getBaseContext(), auth.getUid(), Toast.LENGTH_LONG).show();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        submit = findViewById(R.id.submitPengurang);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        submit.setOnClickListener(this);



        getWindow().setLayout(width,height/3);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submitPengurang:
                setPengurang();
                finish();
                break;
        }

    }

    public void setPengurang(){
        dataPengurang = Integer.parseInt(uangSisa) - Integer.parseInt( Pengurang.getText().toString());
        wishlistRef.whereEqualTo("uuid",auth.getUid())
                .whereEqualTo("wish", namawish)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            if (dataPengurang < 0){
                                Toast.makeText(getBaseContext(), "Nominal Yang Dimasukan Kebanyakan", Toast.LENGTH_LONG).show();
                            }
                            else if (dataPengurang == 0){
                                wishlistRef.document(document.getId()).update("status", "Terpenuhi");
                            }
                            wishlistRef.document(document.getId()).update("uang", Integer.toString(dataPengurang));
                        }
                    }
                });
    }
}
