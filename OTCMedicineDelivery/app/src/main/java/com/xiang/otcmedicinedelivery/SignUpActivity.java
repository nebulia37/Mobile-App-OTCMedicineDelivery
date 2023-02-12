package com.xiang.otcmedicinedelivery;

import java.util.*;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.view.*;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;


public class SignUpActivity extends AppCompatActivity {
    Button btnSignUp;
    EditText edtEmail, edtPassword, edtName;
    String txtEmail, txtPassword, txtName;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtEmail = findViewById(R.id.edtSignUpEmail);
        edtPassword = findViewById(R.id.edtSignUpPassword);
        btnSignUp = findViewById(R.id.btnSignUp);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtEmail = edtEmail.getText().toString().trim();
                txtPassword = edtPassword.getText().toString().trim();

                SignUpUser();
//                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
//                startActivity(intent);
            }
        });
    }

    private void SignUpUser() {
        btnSignUp.setVisibility(View.INVISIBLE);

        mAuth.createUserWithEmailAndPassword(txtEmail, txtPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(SignUpActivity.this, "Data Stored Successfully !", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUpActivity.this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                btnSignUp.setVisibility(View.VISIBLE);
            }
        });

    }
}