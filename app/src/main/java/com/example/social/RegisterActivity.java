package com.example.social;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG ="df" ;
    private FirebaseAuth mAuth;

    public EditText name,email,phone,password;
    public Button register;
    public TextView signIn;
    public String names,emails,phones,passwords;
    public ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();


        name=findViewById(R.id.name);
        name.setOnClickListener(this);

        email=findViewById(R.id.email);
        email.setOnClickListener(this);

        phone=findViewById(R.id.phone);
        phone.setOnClickListener(this);

        password=findViewById(R.id.password);
        password.setOnClickListener(this);

        register=(Button)findViewById(R.id.registerButton);
        register.setOnClickListener(this);

        signIn=findViewById(R.id.sign_in_text);
        signIn.setOnClickListener(this);

        progressBar=findViewById(R.id.progressBar);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_in_text:
            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            break;

            case R.id.registerButton:
                registerUser();
                break;

        }

    }
    public void registerUser(){
        names=name.getText().toString().trim();
        emails=email.getText().toString().trim();
        phones=phone.getText().toString().trim();
       passwords=password.getText().toString().trim();

        if(names.isEmpty()){
            name.setError("Full name Required");
            name.requestFocus();
            return;
        }
        if(emails.isEmpty()){
            email.setError("Full name Required");
            email.requestFocus();
            return;
        }
        if(phones.isEmpty()){
            phone.setError("Full name Required");
            phone.requestFocus();
            return;
        }
        if(passwords.isEmpty()){
            password.setError("Full name Required");
            password.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(emails).matches()){
            email.setError("Invalid email");
            email.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);


        mAuth.createUserWithEmailAndPassword(emails,passwords)
                .addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.e(TAG, "registerUser: dfffffffffffffffffffffffffffffffffff" );

                            User u=new User(names,emails,phones);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(u).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this,"User Registered",Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));

                                        progressBar.setVisibility(View.GONE);

                                    }
                                    else{
                                        Toast.makeText(RegisterActivity.this,"User Registeration failed",Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);

                                    }

                                }
                            });
                        }
                        else{
                            Toast.makeText(RegisterActivity.this,"User Registerationnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn failed",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);

                        }
                    }
                });

    }
}