package com.example.tabungangan.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
//import com.anychart.sample.R;
import java.util.ArrayList;
import java.util.List;

public class Ringkasan extends Fragment {

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



//        AnyChartView anyChartView = view.findViewById(R.id.any_chart_view_pemasukan);
//        anyChartView.setProgressBar(view.findViewById(R.id.progress_bar));
//
//        Pie pie = AnyChart.pie();
//
//        pie.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
//            @Override
//            public void onClick(Event event) {
//                Toast.makeText(Ringkasan.this.getContext(), event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        List<DataEntry> data = new ArrayList<>();
//        data.add(new ValueDataEntry("Apples", 6371664));
//        data.add(new ValueDataEntry("Pears", 789622));
////        data.add(new ValueDataEntry("Bananas", 7216301));
////        data.add(new ValueDataEntry("Grapes", 1486621));
////        data.add(new ValueDataEntry("Oranges", 1200000));
//
//        pie.data(data);
//
//        pie.title("Fruits imported in 2015 (in kg)");
//
//        pie.labels().position("outside");
//
//        pie.legend().title().enabled(true);
//        pie.legend().title()
//                .text("Retail channels")
//                .padding(0d, 0d, 10d, 0d);
//
//        pie.legend()
//                .position("center-bottom")
//                .itemsLayout(LegendLayout.HORIZONTAL)
//                .align(Align.CENTER);
//
//        anyChartView.setChart(pie);
////        anyChartView.setChart(pie);




    }
}
