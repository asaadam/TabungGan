package com.example.tabungangan.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.tabungangan.R;
import com.example.tabungangan.activities.Login;
import com.example.tabungangan.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profil extends Fragment implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private static final String TAG = "PROFIL";
    private User logged;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
         logged = new User(mAuth.getCurrentUser());

        return  inflater.inflate(R.layout.fragment_profil, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button mLogoutButton=view.findViewById(R.id.button_logout);
        mLogoutButton.setOnClickListener(this);
        TextView tv_nama = view.findViewById(R.id.tv_nama);
        Log.d(TAG,logged.getNama());
        TextView tv_phoneNumber = view.findViewById(R.id.tv_phoneNumber);
        TextView tv_email = view.findViewById(R.id.tv_email);
        tv_email.setText(logged.getEmail());
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
