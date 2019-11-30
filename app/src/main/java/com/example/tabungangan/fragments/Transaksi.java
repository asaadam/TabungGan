package com.example.tabungangan.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tabungangan.R;
import com.example.tabungangan.adapters.TransaksiAdapter;
import com.example.tabungangan.helpers.MonthYearPicker;
import com.example.tabungangan.models.TransaksiModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;

public class Transaksi extends Fragment{
    private FirebaseAuth auth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference transaksiRef = db.collection("transaksi");
    private Query query;
    private FirestoreRecyclerOptions<TransaksiModel> options;

    private TransaksiAdapter adapter;
    private RecyclerView recyclerView;
    private TextView tv_jumlah_pengeluaran;
    private Button btn_bulan_tahun;

    private String bulan;
    private String tahun;
    private String temp_bulan_dan_tahun;
    private String[] bulan_dan_tahun;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_transaksi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();

        tv_jumlah_pengeluaran = view.findViewById(R.id.tv_pengeluaran_nilai);
        btn_bulan_tahun = view.findViewById(R.id.button_bulan_tahun);
        recyclerView = view.findViewById(R.id.rv_transaksi_list);

        bulan = Integer.toString(Calendar.getInstance().get(Calendar.MONTH)+1);
        tahun = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));

        btn_bulan_tahun.setText(bulan+"/"+tahun);
        setupRecyclerView(bulan, tahun);

        btn_bulan_tahun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonthYearPicker pickerDialog = new MonthYearPicker();
                pickerDialog.show(getFragmentManager(), "MonthYearPicker");
                pickerDialog.setListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int dataTahun, int dataBulan, int i2) {
                        btn_bulan_tahun.setText(dataBulan+"/"+dataTahun);
                        temp_bulan_dan_tahun = btn_bulan_tahun.getText().toString();
                        bulan_dan_tahun = temp_bulan_dan_tahun.split("/");
                        if(bulan_dan_tahun[0].length()==1){
                            bulan = "0"+bulan_dan_tahun[0];
                        }
                        else if(bulan_dan_tahun[0].length()==2){
                            bulan = bulan_dan_tahun[0];
                        }
                        tahun = bulan_dan_tahun[1];
                        adapter.stopListening();
                        setupRecyclerView(bulan, tahun);
                        adapter.startListening();
                    }
                });
            }
        });
        setupJumlah("08","2019");
    }

    public void setupRecyclerView(String bulan, String tahun){
        query = transaksiRef.whereEqualTo("uuid",auth.getUid())
                .whereEqualTo("bulan", bulan)
                .whereEqualTo("tahun", tahun)
                .orderBy("tanggal", Query.Direction.ASCENDING);
        options = new FirestoreRecyclerOptions.Builder<TransaksiModel>()
                .setQuery(query, TransaksiModel.class)
                .build();

        adapter = new TransaksiAdapter(options);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    public void setupJumlah(String bulan, String tahun){
        transaksiRef.whereEqualTo("uuid",auth.getUid())
                .whereEqualTo("bulan", bulan)
                .whereEqualTo("tahun", tahun)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Sukses", document.getId() + " => " + document.getData().get("jumlah"));
                            }
                        } else {
                            Log.d("Error", "Error getting documents: ", task.getException());
                        }
                    }
                });
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
}
