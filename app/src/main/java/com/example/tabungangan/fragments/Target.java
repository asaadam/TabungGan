package com.example.tabungangan.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;


import android.app.DatePickerDialog;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.example.tabungangan.R;
import com.example.tabungangan.activities.PopUp;
import com.example.tabungangan.activities.Wish;
import com.example.tabungangan.adapters.TransaksiAdapter;
import com.example.tabungangan.adapters.WishlistAdapter;
import com.example.tabungangan.helpers.MonthYearPicker;
import com.example.tabungangan.models.WishlistModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Target extends Fragment implements View.OnClickListener {

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private TextView tvDateresult;
    private Button btnDatePicker;

    private FirebaseAuth auth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference wishlistRef = db.collection("wishlist");
    private Query query;
    private FirestoreRecyclerOptions<WishlistModel> options;

    private WishlistAdapter adapter;
    private RecyclerView recyclerView;
    private Button wishrv;

    private String sisahari;
    private String wish;
    private String jumlahuang;
    private FloatingActionButton mulai;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_target, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view,savedInstanceState);
    auth = FirebaseAuth.getInstance();

    recyclerView = view.findViewById(R.id.rvwish);

    mulai = view.findViewById(R.id.floating_wishlist_button);

    setupRecyclerView();
    mulai.setOnClickListener(this);
    }




    public void setupRecyclerView(){
        query = wishlistRef.whereEqualTo("uuid",auth.getUid())
                .orderBy("wish", Query.Direction.ASCENDING);
        options = new FirestoreRecyclerOptions.Builder<WishlistModel>()
                .setQuery(query, WishlistModel.class)
                .build();

        adapter = new WishlistAdapter(options);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.floating_wishlist_button:
                Intent getWish = new Intent(getContext(), Wish.class);
                startActivity(getWish);
                break;
        }}


    }