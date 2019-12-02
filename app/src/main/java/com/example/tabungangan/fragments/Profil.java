package com.example.tabungangan.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tabungangan.R;
import com.example.tabungangan.activities.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Profil extends Fragment implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore db;
    private CollectionReference userCollection;

    private Button mLogoutButton;
    private TextView tv_nama;
    private TextView tv_saldo;
    private TextView tv_phoneNumber;
    private TextView tv_email;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_profil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        userCollection = db.collection("user");

        getInformasiProfil();

        mLogoutButton = view.findViewById(R.id.button_logout);

        tv_nama = view.findViewById(R.id.tv_nama);
        tv_saldo = view.findViewById(R.id.tv_saldo);
        tv_phoneNumber = view.findViewById(R.id.tv_phoneNumber);
        tv_email = view.findViewById(R.id.tv_email);

        mLogoutButton.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_logout:
                mAuth.signOut();
                Intent intent = new Intent (v.getContext(),Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                Activity activity = (Activity)getContext();
                activity.finish();
                break;
        }
    }

    private void getInformasiProfil(){
        userCollection.whereEqualTo("uuid",user.getUid())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            tv_nama.setText((String) document.getData().get("nama"));
                            tv_saldo.setText(Long.toString((Long) document.getData().get("saldo")));
                            tv_email.setText((String) document.getData().get("email"));
                            tv_phoneNumber.setText((String) document.getData().get("phoneNumber"));
                        }
                    }
                });
    }

}
