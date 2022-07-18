package com.example.social;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class EmailPasswordActivity extends LoginActivity {

//    EditText name,password;
//    Button signIn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_email_password);


//        name=findViewById(R.id.name_sign_in);
//        password=findViewById(R.id.password_sign_in);
//        signIn=findViewById(R.id.sign_in);



    }
    public void userLogin(){
        mAuth = FirebaseAuth.getInstance();

        String e=email.getText().toString().trim();
        String p=password.getText().toString().trim();
        if(e.isEmpty()){
            email.setError("Full name Required");
            email.requestFocus();
            return;
        }
        if(p.isEmpty()){
            password.setError("Full name Required");
            password.requestFocus();
            return;
        }


        if(!Patterns.EMAIL_ADDRESS.matcher(e).matches()){
            email.setError("Invalid email");
            email.requestFocus();
            return;
        }


        mAuth.signInWithEmailAndPassword(e,p)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(EmailPasswordActivity.this,HomeActivity.class));

                        }
                        else{
                            Toast.makeText(EmailPasswordActivity.this,"singin failed",Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
}