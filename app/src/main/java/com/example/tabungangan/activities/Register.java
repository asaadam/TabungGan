package com.example.tabungangan.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tabungangan.R;
import com.example.tabungangan.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class Register extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private EditText mPasswordField,mEmailField,mRePasswordField,mNameField;
    private TextInputLayout mPasswordLayout,mEmailLayout,mRePassowrdLayout,mNameLayout;
    private Button register;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "EmailPasswordRegister";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //layout
        mPasswordLayout=findViewById(R.id.password_layout);
        mRePassowrdLayout=findViewById(R.id.rePassword_layout);
        mNameLayout=findViewById(R.id.name_layout);
        mEmailLayout=findViewById(R.id.email_layout);
        //EditText
        mPasswordField=findViewById(R.id.password_field);
        mRePasswordField=findViewById(R.id.repassword_field);
        mEmailField=findViewById(R.id.email_field);
        mNameField=findViewById(R.id.name_field);
        //Button
        findViewById(R.id.button_login).setOnClickListener(this);
        register = findViewById(R.id.button_registerForm);
        register.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();

    }


    public boolean validateForm(){
        boolean valid = true;
        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)){
            mEmailLayout.setError("Required.");
            valid=false;
        }
        else{
            mEmailLayout.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordLayout.setError("Required");
            valid = false;
        } else {
            mPasswordLayout.setError(null);
        }

        String rePassword = mRePasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mRePassowrdLayout.setError("Required");
            valid = false;
        }
        else  if (!rePassword.equals(password)){
            mRePassowrdLayout.setError("Password Not Match");
            valid = false;
        }
        else {
            mRePassowrdLayout.setError(null);
        }

        String name = mNameField.getText().toString();
        if(TextUtils.isEmpty(name)){
            mNameLayout.setError("Required");
            valid = false;
        }
        else{
            mNameLayout.setError(null);
        }

        return valid;
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        register.setText(R.string.loading);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            User newUser = new
                                    User(mNameField.getText().toString(),mEmailField.getText().toString(),user.getUid(),0);
                            db.collection("user")
                                    .add(newUser)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>()
                                    {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Log.d(TAG, "createUserWithEmail:success");
                                            Log.d(TAG, "DocumentSnapshot added with ID: " +
                                                    documentReference.getId());
                                        Intent intent = new Intent
                                                (Register.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Error adding document", e);
                                        }
                                    });
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Register.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_registerForm :
                createAccount(mEmailField.getText().toString(),mPasswordField.getText().toString());
                break;
            case R.id.button_login :
                Intent intent = new Intent(v.getContext(), Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;

        }

    }


}
