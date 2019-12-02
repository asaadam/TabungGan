package com.example.tabungangan.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.tabungangan.R;
import com.example.tabungangan.fragments.Profil;
import com.example.tabungangan.fragments.Ringkasan;
import com.example.tabungangan.fragments.Target;
import com.example.tabungangan.fragments.Transaksi;
import com.example.tabungangan.models.WishlistModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.DatePicker;
import android.widget.TextView;
import java.util.Calendar;
import android.app.DatePickerDialog;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Wish extends AppCompatActivity implements View.OnClickListener {
    private ImageButton back;
    private String status;
    private FirebaseUser user;
    private static final String TAG = "WishList";
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private TextView tvDateResult, endDate,stas;
    private EditText wishInput, uangInput;
    private Button btDatestar,btDateend,submit;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference wishlistRef = db.collection("wishlist");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish);
        back = findViewById(R.id.backbtn);
        back.setOnClickListener(this);


        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        //Text
        tvDateResult = findViewById(R.id.textviewstartDate);
        endDate = findViewById(R.id.textviewendDate);
        wishInput = findViewById(R.id.inputWish);
        uangInput = findViewById(R.id.inputUang);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        //Button
        btDatestar = findViewById(R.id.startDate);
        btDateend = findViewById(R.id.endDate);
        submit = findViewById(R.id.submit_wishlist);
        //listener
        submit.setOnClickListener(this);
        btDateend.setOnClickListener(this);
        btDatestar.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backbtn:
                finish();
                break;
            case R.id.startDate:
                showDateDialog(tvDateResult);
                break;
            case R.id.endDate:
                showDateDialog(endDate);
                break;
            case R.id.submit_wishlist:
                validateForm();
                insertData();
                finish();
                break;

        }

    }

    private void showDateDialog(final TextView viewDate){
        Calendar newCalender = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);

                viewDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalender.get(Calendar.YEAR), newCalender.get(Calendar.MONTH), newCalender.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public boolean validateForm(){
        boolean valid = true;
        String star = tvDateResult.getText().toString();
        if (TextUtils.isEmpty(star)){
            tvDateResult.setError("Required.");
            valid=false;
        }
        else{
            tvDateResult.setError(null);
        }

        String end = endDate.getText().toString();
        if (TextUtils.isEmpty(end)) {
            endDate.setError("Required");
            valid = false;
        } else {
            endDate.setError(null);
        }

        String uang = uangInput.getText().toString();
        if (TextUtils.isEmpty(uang)) {
            uangInput.setError("Required");
            valid = false;
        }
        else {
            uangInput.setError(null);
        }

        String name = wishInput.getText().toString();
        if(TextUtils.isEmpty(name)){
            wishInput.setError("Required");
            valid = false;
        }
        else{
            wishInput.setError(null);
        }

        return valid;
    }

    private void insertData(){
        status = "Masih Proses";
        WishlistModel wishmu = new WishlistModel(user.getUid(),tvDateResult.getText().toString(),
                endDate.getText().toString(),
                wishInput.getEditableText().toString(),
                uangInput.getEditableText().toString(),
                status
                );
        db.collection("wishlist").add(wishmu)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "AddingWish:success");
                        Log.d(TAG,"DocumentSnapshot added with ID: " +
                                documentReference.getId()); }}
                                )
                .addOnFailureListener((new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        })
        );

    }
}


