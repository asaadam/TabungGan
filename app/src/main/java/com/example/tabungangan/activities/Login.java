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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    private EditText mEmailField,mPasswordField;
    private TextInputLayout mEmailLayout,mPasswordLayout;
    private Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //layout
        mEmailLayout = findViewById(R.id.email_layout);
        mPasswordLayout = findViewById(R.id.password_layout);
        //field
        mEmailField = findViewById(R.id.emailUser);
        mPasswordField = findViewById(R.id.passwordUser);
        //button
        login = findViewById(R.id.button_login);
        login.setOnClickListener(this);
        findViewById(R.id.button_register).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser  currentUser = mAuth.getCurrentUser();

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

        return valid;
    }

    private void signIn (String email,String password){
    if (!validateForm()){
        return;
    }
        login.setText(R.string.loading);
        mAuth.signInWithEmailAndPassword(email,password).
                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    login.setText(R.string.login);
                    FirebaseUser user = mAuth.getCurrentUser();
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    login.setText(R.string.login);
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(Login.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    public void onClick(View v){

            switch (v.getId()){
                case R.id.button_login:
                signIn(mEmailField.getText().toString(),mPasswordField.getText().toString());
                break;
                case R.id.button_register:
                    Intent intent = new Intent(v.getContext(), Register.class);
                    startActivity(intent);
                    break;
            }

    }


}
