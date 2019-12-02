package com.example.tabungangan.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.anychart.APIlib;
import com.example.tabungangan.R;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.example.tabungangan.adapters.TransaksiAdapter;
import com.example.tabungangan.helpers.MonthYearPicker;
import com.example.tabungangan.models.TransaksiModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
//import com.anychart.sample.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Ringkasan extends Fragment {
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
        return inflater.inflate(R.layout.fragment_ringkasan, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final AnyChartView chartPemasukan = view.findViewById(R.id.any_chart_view_pemasukan);
        APIlib.getInstance().setActiveAnyChartView(chartPemasukan);

        final Pie pie_pemasukan = AnyChart.pie();

        pie_pemasukan.setOnClickListener(new ListenersInterface.OnClickListener() {
            @Override
            public void onClick(Event event) {
                Toast.makeText(Ringkasan.this.getContext(), event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
            }
        });

        List<DataEntry> data_pemasukan = new ArrayList<>();
        data_pemasukan.add(new ValueDataEntry("Gaji",40000000));
        data_pemasukan.add(new ValueDataEntry("Tunjangan",75300000));
        data_pemasukan.add(new ValueDataEntry("Bonus",25000000));
        data_pemasukan.add(new ValueDataEntry("Lain-lain",10000000));

        pie_pemasukan.data(data_pemasukan);

        pie_pemasukan.title("Pemasukan Bulan ......");

        chartPemasukan.setChart(pie_pemasukan);



        final AnyChartView chartPengeluaran = view.findViewById(R.id.any_chart_view_pengeluaran);
        APIlib.getInstance().setActiveAnyChartView(chartPengeluaran);

        final Pie pie_pengeluaran = AnyChart.pie();

        pie_pengeluaran.setOnClickListener(new ListenersInterface.OnClickListener() {
            @Override
            public void onClick(Event event) {
                Toast.makeText(Ringkasan.this.getContext(), event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
            }
        });

        List<DataEntry> data_pengeluaran = new ArrayList<>();
        data_pengeluaran.add(new ValueDataEntry("Makanan dan Minuman",30000000));
        data_pengeluaran.add(new ValueDataEntry("Transportasi",30000000));
        data_pengeluaran.add(new ValueDataEntry("Hiburan",25300000));
        data_pengeluaran.add(new ValueDataEntry("Lain-lain",10000000));

        pie_pengeluaran.data(data_pengeluaran);

        pie_pengeluaran.title("Pengeluaran Bulan ......");

        chartPengeluaran.setChart(pie_pengeluaran);
        btn_bulan_tahun = view.findViewById(R.id.btn_bulan_tahun);

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
                        Toast.makeText(Ringkasan.this.getContext(), bulan+" "+tahun, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

//    private void showDateDialog(){
//        Calendar newCalendar = Calendar.getInstance();
//
//        datePickerDialog = new DatePickerDialog(Ringkasan.this.getContext(), new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int tahun, int bulan, int tanggal) {
//                Date dt = new Date(tahun, bulan, tanggal-1);
//                SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE");
//                hariString = dateFormat.format(dt);
//                tanggalString = Integer.toString(tanggal);
//                bulanString = Integer.toString(bulan+1);
//                tahunString = Integer.toString(tahun);
//                if(tanggalString.length()==1){
//                    tanggalString = "0"+tanggalString;
//                }
//                if(bulanString.length()==1){
//                    bulanString = "0"+bulanString;
//                }
//                btn_tanggal.setText(tanggalString+"/"+bulanString+"/"+tahunString);
//            }
//
//        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
//
//        datePickerDialog.show();
//    }
}