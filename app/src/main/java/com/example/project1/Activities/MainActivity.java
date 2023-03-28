package com.example.project1.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.project1.R;
import com.example.project1.view_models.PropertyFragmentViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    PropertyFragmentViewModel propertyFragmentViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseAuth check_user_loggedin = FirebaseAuth.getInstance();
        FirebaseUser currentUser = check_user_loggedin.getCurrentUser();
        if (currentUser != null) {
        } else {
            startActivity(new Intent(MainActivity.this, login.class));
            finish();
        }
        propertyFragmentViewModel = new ViewModelProvider(this).get(PropertyFragmentViewModel.class);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_bar);
        NavController navController = Navigation.findNavController(this,R.id.host_fragment_container);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);

    }
}