package com.example.tabungangan.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.tabungangan.R;
import com.example.tabungangan.fragments.Profil;
import com.example.tabungangan.fragments.Ringkasan;
import com.example.tabungangan.fragments.Target;
import com.example.tabungangan.fragments.Transaksi;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private BottomNavigationView bottomNavigation;
    private FrameLayout fragmentContainer;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragment(new Ringkasan());

        fragmentContainer = findViewById(R.id.fragment_container);
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

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
