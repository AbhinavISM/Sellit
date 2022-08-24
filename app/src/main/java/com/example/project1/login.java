package com.example.project1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    EditText emaillogin, passwordlogin;
    Button loginbutton;
    FirebaseAuth loginauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button regintentbutton = findViewById(R.id.regintentbutton);
        regintentbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.this, register.class));
            }
        });

        emaillogin = findViewById(R.id.emaillogin);
        passwordlogin = findViewById(R.id.passwordlogin);
        loginbutton = findViewById(R.id.loginbutton);
        loginauth = FirebaseAuth.getInstance();
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailentered = emaillogin.getText().toString();
                String passwordentered = passwordlogin.getText().toString();
                if(passwordentered.length()!=0&&emailentered.length()!=0){
                    if (Patterns.EMAIL_ADDRESS.matcher(emailentered).matches()) {
                        loginauth.signInWithEmailAndPassword(emailentered,passwordentered).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(login.this, "succesfully logged in", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(login.this,MainActivity.class));
                                    finish();
                                }
                                else{
                                    Toast.makeText(login.this, "login failed", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                    else{
                        emaillogin.setError("invalid email");
                        emaillogin.requestFocus();
                    }
                }
                else{
                    Toast.makeText(login.this, "please fill all credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}