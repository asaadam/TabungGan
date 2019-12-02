package com.example.tabungangan.fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import com.example.tabungangan.activities.TambahTransaksiActivity;
import com.example.tabungangan.adapters.TransaksiAdapter;
import com.example.tabungangan.helpers.MonthYearPicker;
import com.example.tabungangan.models.TransaksiModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
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
    private TextView tv_jumlah_pemasukan;
    private TextView tv_total_transaksi;
    private Button btn_bulan_tahun;
    private FloatingActionButton floating_btn_tambah_transaksi;

    private String bulan;
    private String tahun;
    private String temp_bulan_dan_tahun;
    private String[] bulan_dan_tahun;

    private String tempJumlahPengeluaran;
    private String tempJumlahPemasukan;

    String bulanSekaran;
    String tahunSekarang;

    private int jumlahPemasukan;
    private int jumlahPengeluaran;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_transaksi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bulanSekaran = Integer.toString(Calendar.getInstance().get(Calendar.MONTH)+1);
        tahunSekarang = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));

        Bundle bundle = this.getArguments();
        bulan = bundle.getString("bulan", bulanSekaran);
        tahun = bundle.getString("tahun", tahunSekarang);

        auth = FirebaseAuth.getInstance();

        tv_jumlah_pengeluaran = view.findViewById(R.id.tv_pengeluaran_nilai);
        tv_jumlah_pemasukan = view.findViewById(R.id.tv_pemasukan_nilai);
        tv_total_transaksi = view.findViewById(R.id.tv_total_nilai);
        btn_bulan_tahun = view.findViewById(R.id.button_bulan_tahun);
        floating_btn_tambah_transaksi = view.findViewById(R.id.floating_btn_tambah_transaksi);
        recyclerView = view.findViewById(R.id.rv_transaksi_list);

        btn_bulan_tahun.setText(bulan+"/"+tahun);
        tv_jumlah_pengeluaran.setTextColor(Color.RED);
        tv_jumlah_pemasukan.setTextColor(Color.parseColor("#7EC544"));
        setupJumlah(bulan,tahun);
        setupRecyclerView(bulan, tahun);

        btn_bulan_tahun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonthYearPicker pickerDialog = new MonthYearPicker();
                pickerDialog.show(getFragmentManager(), "MonthYearPicker");
                pickerDialog.setListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int dataTahun, int dataBulan, int i2) {
                        if(Integer.toString(dataBulan).length()==1){
                            btn_bulan_tahun.setText("0"+dataBulan+"/"+dataTahun);
                        }else{
                            btn_bulan_tahun.setText(dataBulan+"/"+dataTahun);
                        }
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
                        jumlahPemasukan = 0;
                        jumlahPengeluaran = 0;
                        setupJumlah(bulan,tahun);
                    }
                });
            }
        });

        floating_btn_tambah_transaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TambahTransaksiActivity.class);
                startActivity(intent);
            }
        });
    }

    public void setupRecyclerView(String bulan, String tahun){
        query = transaksiRef.whereEqualTo("uuid",auth.getUid())
                .whereEqualTo("bulan", bulan)
                .whereEqualTo("tahun", tahun)
                .orderBy("tanggal", Query.Direction.DESCENDING);
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
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            if(document.getData().get("tipe").equals("Pengeluaran")){
                                tempJumlahPengeluaran = (String) document.getData().get("jumlah");
                                jumlahPengeluaran = jumlahPengeluaran + Integer.parseInt(tempJumlahPengeluaran);
                            }
                            else if(document.getData().get("tipe").equals("Pemasukan")){
                                tempJumlahPemasukan = (String) document.getData().get("jumlah");
                                jumlahPemasukan = jumlahPemasukan + Integer.parseInt(tempJumlahPemasukan);
                            }
                        }
                        tv_jumlah_pengeluaran.setText(Integer.toString(jumlahPengeluaran));
                        tv_jumlah_pemasukan.setText(Integer.toString(jumlahPemasukan));
                        tv_total_transaksi.setText(Integer.toString(jumlahPemasukan-jumlahPengeluaran));
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
