package com.example.social;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.nio.charset.StandardCharsets;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText email;
    Button reset;
    ProgressDialog dialog;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email=findViewById(R.id.email_f);
        reset=findViewById((R.id.reset_password));

        auth=FirebaseAuth.getInstance();
        dialog=new ProgressDialog(this);
        dialog.setTitle("Sending email...");

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resetPassword();
            }
        });

    }
    public void resetPassword(){
        String e=email.getText().toString().trim();
        if(e.isEmpty()){
            email.setError("Email required");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(e).matches()){
            email.setError("Email required");
            email.requestFocus();
            return;

        }
        dialog.show();
        auth.sendPasswordResetEmail(e).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPasswordActivity.this,"Check your email to reset password",Toast.LENGTH_LONG).show();
                    dialog.dismiss();

                }
                else{
                    Toast.makeText(ForgotPasswordActivity.this,"Something went wrong. Try Again !!",Toast.LENGTH_LONG).show();
                    dialog.dismiss();

                }
            }
        });
    }
}