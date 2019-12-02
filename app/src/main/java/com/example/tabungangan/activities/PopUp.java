package com.example.tabungangan.activities;

import androidx.appcompat.app.AppCompatActivity;
import com.example.tabungangan.R;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class PopUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);


        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout(width/2,height/2);


    }
}
