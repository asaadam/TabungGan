package com.example.tabungangan.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.tabungangan.R;
import com.example.tabungangan.activities.Login;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profil extends Fragment implements View.OnClickListener {
    Button mLogoutButton;
    private FirebaseAuth mAuth;
    private static final String TAG = "PROFIL";



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView =  inflater.inflate(R.layout.fragment_profil, container, false);
        mAuth = FirebaseAuth.getInstance();
        mLogoutButton=myView.findViewById(R.id.button_logout);
        mLogoutButton.setOnClickListener(this);
        return myView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_logout:
                mAuth.signOut();
                Intent intent = new Intent (v.getContext(),Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                Activity activity = (Activity)getContext();
                activity.finish();
                break;
        }
    }

}
