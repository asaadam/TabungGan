package com.example.tabungangan.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.tabungangan.R;
import com.example.tabungangan.models.TransaksiModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TambahTransaksiActivity extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference transaksiRef = db.collection("transaksi");

    private ImageButton btn_back_tambah_transaksi;
    private Button btn_tanggal, btn_simpan;
    private EditText et_tipe, et_kategori, et_nominal;
    private String tipe, kategori, nominal;
    private String hariString, tanggalString, bulanString, tahunString;

    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_transaksi);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        btn_back_tambah_transaksi = findViewById(R.id.btn_back_tambah_transaksi);
        btn_tanggal = findViewById(R.id.btn_tanggal);
        btn_simpan = findViewById(R.id.btn_simpan);
        et_tipe = findViewById(R.id.tipe_field);
        et_kategori = findViewById(R.id.kategori_field);
        et_nominal = findViewById(R.id.nominal_field);

        btn_back_tambah_transaksi.setOnClickListener(this);
        btn_tanggal.setOnClickListener(this);
        btn_simpan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back_tambah_transaksi:
                finish();
                break;
            case R.id.btn_tanggal:
                showDateDialog();
                break;
            case R.id.btn_simpan:
                saveData();
                break;
        }
    }

    private void showDateDialog(){
        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int tahun, int bulan, int tanggal) {
                Date dt = new Date(tahun, bulan, tanggal-1);
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE");
                hariString = dateFormat.format(dt);
                tanggalString = Integer.toString(tanggal);
                bulanString = Integer.toString(bulan+1);
                tahunString = Integer.toString(tahun);
                if(tanggalString.length()==1){
                    tanggalString = "0"+tanggalString;
                }
                if(bulanString.length()==1){
                    bulanString = "0"+bulanString;
                }
                btn_tanggal.setText(tanggalString+"/"+bulanString+"/"+tahunString);
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void saveData(){
        tipe = et_tipe.getText().toString();
        kategori = et_kategori.getText().toString();
        nominal = et_nominal.getText().toString();
        TransaksiModel transaksi = new TransaksiModel(hariString, tanggalString, bulanString, tahunString, tipe, kategori, nominal, user.getUid());
        transaksiRef.add(transaksi)
        .addOnSuccessListener(new OnSuccessListener<DocumentReference>(){
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.putExtra("fragment", "Transaksi");
                intent.putExtra("bulan", bulanString);
                intent.putExtra("tahun", tahunString);
                startActivity(intent);
                Toast.makeText(getBaseContext(), "Berhasil Menyimpan Transaksi", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("Tambah Transaksi", "Error adding document", e);
            }
        });
    }
}