package com.xiang.otcmedicinedelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText edtEmail, edtPassword;
    Button btnSignIn;
    String txtEmail, txtPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edtSignInEmail);
        edtPassword = findViewById(R.id.edtSignInPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
//        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        mAuth = FirebaseAuth.getInstance();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtEmail = edtEmail.getText().toString().trim();
                txtPassword = edtPassword.getText().toString().trim();

                if (!TextUtils.isEmpty(txtEmail)) {
                    if (!TextUtils.isEmpty(txtPassword)) {
                        SignInUser();
                    } else {
                        edtPassword.setError("Password Field can't be empty");
                    }
                } else {
                    edtEmail.setError("Email Field can't be empty");
                }
            }
        });
    }

    private void SignInUser() {
        btnSignIn.setVisibility(View.INVISIBLE);

        mAuth.signInWithEmailAndPassword(txtEmail, txtPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(LoginActivity.this, "Login Successful !", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, PharmacyActivity.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "Error - " + e.getMessage(), Toast.LENGTH_SHORT).show();
                btnSignIn.setVisibility(View.VISIBLE);
            }
        });

    }

    public void onClickSignUp(View view) {
        //https://stackoverflow.com/questions/13194081/how-to-open-a-second-activity-on-click-of-button-in-android-app
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }
}