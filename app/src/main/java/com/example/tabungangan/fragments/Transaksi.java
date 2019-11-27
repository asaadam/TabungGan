package com.example.tabungangan.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tabungangan.R;
import com.example.tabungangan.helpers.MonthYearPicker;

public class Transaksi extends Fragment implements View.OnClickListener{

    TextView coba;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_transaksi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        coba = view.findViewById(R.id.coba);
//
//        coba.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.coba:
//                final Context context = v.getContext();
//                MonthYearPicker pickerDialog = new MonthYearPicker();
//                pickerDialog.show(getFragmentManager(), "MonthYearPicker");
//                pickerDialog.setListener(new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker datePicker, int year, int month, int i2) {
//                        Toast.makeText(context, month + "-" + year, Toast.LENGTH_SHORT).show();
//                    }
//                });
//                break;
        }
    }
}
