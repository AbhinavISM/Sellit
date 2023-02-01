package com.example.project1.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.project1.R;
import com.example.project1.PropertyFragmentViewModel;
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
//            startActivity(new Intent(MainActivity.this, recycler_activity.class));
//            finish();
        } else {
            startActivity(new Intent(MainActivity.this, login.class));
            finish();
        }
        propertyFragmentViewModel = new ViewModelProvider(this).get(PropertyFragmentViewModel.class);


//        replaceFragment(new HomeFragment());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_bar);

//        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                Fragment selected_fragment;
//                switch (item.getItemId()){
//                    case  R.id.homeFragment:
//                        selected_fragment = new HomeFragment();
//                        replaceFragment(selected_fragment);
//                        break;
//                    case R.id.propertyFragment:
//                        selected_fragment = new PropertyFragment();
//                        replaceFragment(selected_fragment);
//                        break;
//                    case R.id.addFragment:
//                        selected_fragment = new AddFragment();
//                        replaceFragment(selected_fragment);
//                        break;
//                }
//                return true;
//            }
//        });

        NavController navController = Navigation.findNavController(this,R.id.host_fragment_container);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);

    }

//    private void replaceFragment(Fragment selected_fragment) {
//        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,selected_fragment).commit();
//    }
}