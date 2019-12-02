package com.example.tabungangan.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
//import com.anychart.sample.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Ringkasan extends Fragment {
    private static FirebaseAuth auth;
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static CollectionReference transaksiRef = db.collection("transaksi");
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
    private static int [] pemasukan = new int[4];
    private static int [] pengeluaran = new int[7];



    String bulanSekaran;
    String tahunSekarang;

    private int jumlahPemasukan;
    private int jumlahPengeluaran;

    /**
     * Kategori Pemasukan: Gaji, Tunjangan, Bonus, dan Lain-lain;
     * Kategori Pengeluaran: Makanan, Belanja, Hobi, Transportasi, Kesehatan, Pendidikan, dan Lain-lain;
     *
     * */

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ringkasan, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();

        final AnyChartView chartPemasukan = view.findViewById(R.id.any_chart_view_pemasukan);
        APIlib.getInstance().setActiveAnyChartView(chartPemasukan);

        final Pie pie_pemasukan = AnyChart.pie();

        pie_pemasukan.setOnClickListener(new ListenersInterface.OnClickListener() {
            @Override
            public void onClick(Event event) {
                Toast.makeText(Ringkasan.this.getContext(), event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
            }
        });

        bulan = Integer.toString(Calendar.getInstance().get(Calendar.MONTH)+1);
        tahun = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));

//        Toast.makeText(Ringkasan.this.getContext(), bulan+"/"+tahun, Toast.LENGTH_SHORT).show();



        Log.d("Bulan tahun", String.valueOf(bulan)+" "+String.valueOf(tahun));

        Log.d("Pemasukan char indeks 0", Integer.toString(pemasukan[0]));
        Log.d("Pemasukan char indeks 1", String.valueOf(pemasukan[1]));
        Log.d("Pemasukan char indeks 2", String.valueOf(pemasukan[2]));
        Log.d("Pemasukan char indeks 3", String.valueOf(pemasukan[3]));

        List<DataEntry> data_pemasukan = new ArrayList<>();
        data_pemasukan.add(new ValueDataEntry("Gaji", pemasukan[0]));
        data_pemasukan.add(new ValueDataEntry("Tunjangan", pemasukan[1]));
        data_pemasukan.add(new ValueDataEntry("Bonus",pemasukan[2]));
        data_pemasukan.add(new ValueDataEntry("Lain-lain",  pemasukan[3]));

        pie_pemasukan.data(data_pemasukan);

        pie_pemasukan.title("PEMASUKAN");

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

        /**
         * Kategori Pemasukan: Gaji, Tunjangan, Bonus, dan Lain-lain;
         * Kategori Pengeluaran: Makanan, Belanja, Hobi, Transportasi, Kesehatan, Pendidikan, dan Lain-lain;
         *
         * */


        List<DataEntry> data_pengeluaran = new ArrayList<>();
        data_pengeluaran.add(new ValueDataEntry("Makanan", pengeluaran[0]));

        data_pengeluaran.add(new ValueDataEntry("Belanja",pengeluaran[1]));
        data_pengeluaran.add(new ValueDataEntry("Hobi",pengeluaran[2]));
        data_pengeluaran.add(new ValueDataEntry("Transportasi",pengeluaran[3]));
        data_pengeluaran.add(new ValueDataEntry("Kesehatan",pengeluaran[4]));
        data_pengeluaran.add(new ValueDataEntry("Pendidikan",pengeluaran[5]));
        data_pengeluaran.add(new ValueDataEntry("Lain-lain",pengeluaran[6]));


        pie_pengeluaran.data(data_pengeluaran);

        pie_pengeluaran.title("PENGELUARAN");

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
                        getPemasukan(bulan, tahun);
                    }
                });
                getPemasukan(bulan, tahun);
                getPengeluaran(bulan, tahun);
            }
        });

    }

    public static void getPemasukan(String bulan, String tahun){
//        long [] pemasukan = new long[]{0,0,0,0};
        pemasukan [0] = 0;
        pemasukan [1] = 0;
        pemasukan [2] = 0;
        pemasukan [3] = 0;
        /**
         * Pemasukan [0] : Gaji
         * Pemasukan [1] : Tunjangan
         * Pemasukan [2] : Bonus
         * Pemasukan [3] : Lain-lain
         * */

        transaksiRef.whereEqualTo("uuid",auth.getUid())
                .whereEqualTo("bulan", bulan)
                .whereEqualTo("tahun", tahun)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
//                            Log.d("Data", (String) document.getData().get("tipe"));
                            if(document.getData().get("tipe").equals("Pemasukan") && document.getData().get("kategori").equals("Gaji")){
                                Log.d("masukdata", String.valueOf(document.getData().get("jumlah")));
                                pemasukan[0] = pemasukan[0] + Integer.parseInt((String) document.getData().get("jumlah"));
                            }
                            else if(document.getData().get("tipe").equals("Pemasukan") && document.getData().get("kategori").equals("Tunjangan")){
                                pemasukan[1] = pemasukan[1] + Integer.parseInt((String) document.getData().get("jumlah"));
                            }
                            else if(document.getData().get("tipe").equals("Pemasukan") && document.getData().get("kategori").equals("Bonus")){
                                pemasukan[2] = pemasukan[2] + Integer.parseInt((String) document.getData().get("jumlah"));
                            }
                            else if(document.getData().get("tipe").equals("Pemasukan") && document.getData().get("kategori").equals("Lain-lain")){
                                pemasukan[3] =pemasukan[3] + Integer.parseInt((String) document.getData().get("jumlah"));
                            }
                        }

                        Log.d("Pemasukan indeks 0", Integer.toString(pemasukan[0]));
                        Log.d("Pemasukan indeks 1", String.valueOf(pemasukan[1]));
                        Log.d("Pemasukan indeks 2", String.valueOf(pemasukan[2]));
                        Log.d("Pemasukan indeks 3", String.valueOf(pemasukan[3]));


//                        Log.d("Pemasukan indeks 0", String.valueOf(pemasukan[0]));
//                        Log.d("Pemasukan indeks 1", String.valueOf(pemasukan[1]));
//                        Log.d("Pemasukan indeks 2", String.valueOf(pemasukan[2]));
//                        Log.d("Pemasukan indeks 3", String.valueOf(pemasukan[3]));

//                        tv_jumlah_pengeluaran.setText(formatRupiah.format(jumlahPengeluaran));
//                        tv_jumlah_pemasukan.setText(formatRupiah.format(jumlahPemasukan));
//                        tv_total_transaksi.setText(formatRupiah.format(jumlahPemasukan-jumlahPengeluaran));
                    }
                });

    }

    public static void getPengeluaran(String bulan, String tahun){
//        long [] pemasukan = new long[]{0,0,0,0};
        pengeluaran [0] = 0;
        pengeluaran [1] = 0;
        pengeluaran [2] = 0;
        pengeluaran [3] = 0;
        pengeluaran [4] = 0;
        pengeluaran [5] = 0;
        pengeluaran [6] = 0;

        /**
         *    * Kategori Pengeluaran: Makanan, Belanja, Hobi, Transportasi, Kesehatan, Pendidikan, dan Lain-lain;
         * Pemasukan [0] : Gaji
         * Pemasukan [1] : Tunjangan
         * Pemasukan [2] : Bonus
         * Pemasukan [3] : Lain-lain
         * */

        transaksiRef.whereEqualTo("uuid",auth.getUid())
                .whereEqualTo("bulan", bulan)
                .whereEqualTo("tahun", tahun)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
//                            Log.d("Data", (String) document.getData().get("tipe"));
                            if(document.getData().get("tipe").equals("Pengeluaran") && document.getData().get("kategori").equals("Makanan")){
                                Log.d("masukdata", String.valueOf(document.getData().get("jumlah")));
                                pengeluaran[0] = pengeluaran[0] + Integer.parseInt((String) document.getData().get("jumlah"));
                            }
                            else if(document.getData().get("tipe").equals("Pengeluaran") && document.getData().get("kategori").equals("Belanja")){
                                pengeluaran[1] = pengeluaran[1] + Integer.parseInt((String) document.getData().get("jumlah"));
                            }
                            else if(document.getData().get("tipe").equals("Pemasukan") && document.getData().get("kategori").equals("Hobi")){
                                pengeluaran[2] = pengeluaran[2] + Integer.parseInt((String) document.getData().get("jumlah"));
                            }
                            else if(document.getData().get("tipe").equals("Pemasukan") && document.getData().get("kategori").equals("Transportasi")){
                                pengeluaran[3] =pengeluaran[3] + Integer.parseInt((String) document.getData().get("jumlah"));
                            }
                            else if(document.getData().get("tipe").equals("Pengeluaran") && document.getData().get("kategori").equals("Kesehatan")){
                                pengeluaran[4] = pengeluaran[4] + Integer.parseInt((String) document.getData().get("jumlah"));
                            }
                            else if(document.getData().get("tipe").equals("Pemasukan") && document.getData().get("kategori").equals("Pendidikan")){
                                pengeluaran[5] = pengeluaran[5] + Integer.parseInt((String) document.getData().get("jumlah"));
                            }
                            else if(document.getData().get("tipe").equals("Pemasukan") && document.getData().get("kategori").equals("Lain-lain")){
                                pengeluaran[6] =pengeluaran[6] + Integer.parseInt((String) document.getData().get("jumlah"));
                            }
                        }

                        Log.d("Pemasukan indeks 0", Integer.toString(pemasukan[0]));
                        Log.d("Pemasukan indeks 1", String.valueOf(pemasukan[1]));
                        Log.d("Pemasukan indeks 2", String.valueOf(pemasukan[2]));
                        Log.d("Pemasukan indeks 3", String.valueOf(pemasukan[3]));


//                        Log.d("Pemasukan indeks 0", String.valueOf(pemasukan[0]));
//                        Log.d("Pemasukan indeks 1", String.valueOf(pemasukan[1]));
//                        Log.d("Pemasukan indeks 2", String.valueOf(pemasukan[2]));
//                        Log.d("Pemasukan indeks 3", String.valueOf(pemasukan[3]));

//                        tv_jumlah_pengeluaran.setText(formatRupiah.format(jumlahPengeluaran));
//                        tv_jumlah_pemasukan.setText(formatRupiah.format(jumlahPemasukan));
//                        tv_total_transaksi.setText(formatRupiah.format(jumlahPemasukan-jumlahPengeluaran));
                    }
                });

    }




}