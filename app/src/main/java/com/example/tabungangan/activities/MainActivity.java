package com.example.tabungangan.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.tabungangan.R;
import com.example.tabungangan.fragments.Profil;
import com.example.tabungangan.fragments.Ringkasan;
import com.example.tabungangan.fragments.Target;
import com.example.tabungangan.fragments.Transaksi;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private BottomNavigationView bottomNavigation;
    private Fragment fragment;

    String dataFragment, bulan, tahun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataFragment = "Ringkasan";

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            dataFragment = extras.getString("fragment");
            bulan = extras.getString("bulan");
            tahun = extras.getString("tahun");
        }

        fragmentToShow(dataFragment);

        bottomNavigation = findViewById(R.id.bottom_navigation);

        bottomNavigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        fragment = null;
        switch (item.getItemId()){
            case R.id.navigation_ringkasan:
                fragment = new Ringkasan();
                break;
            case R.id.navigation_transaksi:
                fragment = new Transaksi();
                break;
            case R.id.navigation_target:
                fragment = new Target();
                break;
            case R.id.navigation_profil:
                fragment = new Profil();
                break;
        }
        return loadFragment(fragment);
    }

    private void fragmentToShow(String dataFragment){
        switch (dataFragment){
            case "Ringkasan":
                loadFragment(new Ringkasan());
                break;
            case "Transaksi":
                loadFragment(new Transaksi());
                break;
            case "Target":
                loadFragment(new Target());
                break;
            case "Profil":
                loadFragment(new Profil());
                break;
        }
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            Bundle bundle = new Bundle();
            bundle.putString("bulan", bulan);
            bundle.putString("tahun", tahun);
            fragment.setArguments(bundle);
            return true;
        }
        return false;
    }
}
