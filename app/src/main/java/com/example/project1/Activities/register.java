package com.example.project1.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project1.R;
import com.example.project1.registered_user_info;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {
    EditText nameregister1, emailreg1, passwordreg1, passwordconfirmreg1;
    Button registerbutton1;
    FirebaseAuth regauth1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nameregister1 = findViewById(R.id.nameregister);
        emailreg1 = findViewById(R.id.emailreg);
        passwordreg1 = findViewById(R.id.passwordreg);
        passwordconfirmreg1 = findViewById(R.id.passwordconfirmreg);
        registerbutton1 = findViewById(R.id.registerbutton);
        regauth1 = FirebaseAuth.getInstance();

        registerbutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameregister1.getText().toString();
                String password = passwordreg1.getText().toString();
                String email = emailreg1.getText().toString();
                String confirmpassword = passwordconfirmreg1.getText().toString();
                if(name.length()!=0&&password.length()!=0&&email.length()!=0) {

                    if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                        if(password.length() >= 8){
                            if(password.matches(confirmpassword)){
                                regauth1.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){

                                            registered_user_info newuser = new registered_user_info(name, password,email);
//                                            FirebaseDatabase db = FirebaseDatabase.getInstance();
//                                            DatabaseReference dbref = db.getReference("users");
                                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(newuser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
//                                                    newuser.setEmail("");
//                                                    newuser.setName("");
//                                                    newuser.setPassword("");
//                                                    startActivity(new Intent(register.this,recycler_activity.class));
//                                                    finish();
                                                    if(task.isSuccessful()){
                                                        Toast.makeText(register.this, "succesfullyregsitered", Toast.LENGTH_LONG).show();
                                                        startActivity(new Intent(register.this, MainActivity.class));
                                                        finish();
                                                    }
                                                    else{
                                                        Toast.makeText(register.this, "registration failed", Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });
                                        }
                                        else{
                                            Toast.makeText(register.this, "registration failed", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                            else{
                                passwordconfirmreg1.setError("password doesn't match");
                                passwordconfirmreg1.requestFocus();
                            }
                        }
                        else{
                            passwordreg1.setError("fill atleast 8 characters");
                            passwordreg1.requestFocus();
                        }
                    } else {
                        emailreg1.setError("fill valid email");
                        emailreg1.requestFocus();
                    }
                }
                else{
                    Toast.makeText(register.this, "fill in all credentials", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}