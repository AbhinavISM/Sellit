package com.example.project1;

import static com.example.project1.R.menu.recycler_acttivity_menu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class recycler_activity extends AppCompatActivity implements recyclerInterface{

    RecyclerView recyclerView;
    public static property_adapter adapterofprop;
    List<Property_model_class> property_list;
    LinearLayoutManager layoutManager;

    @Override
    public void registerForContextMenu(View view) {
        super.registerForContextMenu(view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        FirebaseAuth check_user_loggedin = FirebaseAuth.getInstance();
        FirebaseUser currentUser = check_user_loggedin.getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent(recycler_activity.this, login.class));
            finish();
        }
        initproperty_data();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater recycler_activity_menu_inflater = getMenuInflater();
        recycler_activity_menu_inflater.inflate(recycler_acttivity_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout_item:
                FirebaseAuth.getInstance().signOut();
                FirebaseAuth check_user_loggedin = FirebaseAuth.getInstance();
                FirebaseUser currentUser = check_user_loggedin.getCurrentUser();
                if (currentUser == null) {
                    Toast.makeText(recycler_activity.this,"logged out succesfully",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(recycler_activity.this,login.class));
                    finish();
                }
                else{
                    Toast.makeText(recycler_activity.this,"logout failed",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.add_property_item:
                startActivity(new Intent(recycler_activity.this,new_property.class));
                break;
            case R.id.proflie_item:
                startActivity(new Intent(recycler_activity.this,this_user.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    void initrecycler() {
        recyclerView=findViewById(R.id.RecyclerViewforproperty);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapterofprop= new property_adapter(property_list,this,this);
        recyclerView.setAdapter(adapterofprop);
        adapterofprop.notifyDataSetChanged();
    }

    private void initproperty_data() {
        property_list = new ArrayList<>();
        // if i dont intialise this one item for once by myself, i dont know why my firebase data fetchin gloop below wont work
//        property_list.add(new Property_model_class("1234", "jaha taha", "bahut saara paisa", "bahut banhiya property hain","sab ka maalik ek","https://firebasestorage.googleapis.com/v0/b/project1-2d029.appspot.com/o/uploads%2F1660458189951.jpg?alt=media&token=46e81358-0a2f-4c51-a28d-9c60a40a2053","NO_ID","NO_ID"));
//        property_list.add(new Property_model_class("1234", "jaha taha", "bahut saara paisa", "bahut banhiya property hain","sab ka maalik ek","https://firebasestorage.googleapis.com/v0/b/project1-2d029.appspot.com/o/uploads%2F1658759605827.jpg?alt=media&token=54389f6a-b0d9-4687-8adb-c0a0497951ed"));
//        property_list.add(new Property_model_class("1234", "jaha taha", "bahut saara paisa", "bahut banhiya property hain","sab ka maalik ek",R.drawable.testimg2));
//        property_list.add(new Property_model_class("1234", "jaha taha", "bahut saara paisa", "bahut banhiya property hain","sab ka maalik ek",R.drawable.testimg1));
//        property_list.add(new Property_model_class("1234", "jaha taha", "bahut saara paisa", "bahut banhiya property hain","sab ka maalik ek",R.drawable.testimg3));
//        property_list.add(new Property_model_class("1234", "jaha taha", "bahut saara paisa", "bahut banhiya property hain","sab ka maalik ek",R.drawable.testimg1));
//        property_list.add(new Property_model_class("1234", "jaha taha", "bahut saara paisa", "bahut banhiya property hain","sab ka maalik ek",R.drawable.testimg1));
//        property_list.add(new Property_model_class("1234", "jaha taha", "bahut saara paisa", "bahut banhiya property hain","sab ka maalik ek",R.drawable.testimg2));
//        property_list.add(new Property_model_class("1234", "jaha taha", "bahut saara paisa", "bahut banhiya property hain","sab ka maalik ek",R.drawable.testimg2));
//        property_list.add(new Property_model_class("1234", "jaha taha", "bahut saara paisa", "bahut banhiya property hain","sab ka maalik ek",R.drawable.testimg3));
//        property_list.add(new Property_model_class("1234", "jaha taha", "bahut saara paisa", "bahut banhiya property hain","sab ka maalik ek",R.drawable.testimg2));
//        property_list.add(new Property_model_class("1234", "jaha taha", "bahut saara paisa", "bahut banhiya property hain","sab ka maalik ek",R.drawable.testimg1));
//        property_list.add(new Property_model_class("1234", "jaha taha", "bahut saara paisa", "bahut banhiya property hain","sab ka maalik ek",R.drawable.testimg1));
//        property_list.add(new Property_model_class("1234", "jaha taha", "bahut saara paisa", "bahut banhiya property hain","sab ka maalik ek",R.drawable.testimg1));
//        property_list.add(new Property_model_class("1234", "jaha taha", "bahut saara paisa", "bahut banhiya property hain","sab ka maalik ek",R.drawable.testimg2));
//        property_list.add(new Property_model_class("1234", "jaha taha", "bahut saara paisa", "bahut banhiya property hain","sab ka maalik ek",R.drawable.testimg1));

        FirebaseDatabase.getInstance().getReference("property added by all users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                property_list.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Property_model_class property_model_data = dataSnapshot.getValue(Property_model_class.class);
                    property_list.add(property_model_data);
                    Log.d("run hua", "this time");
                    Log.d("hello",property_model_data.getAdress());
                    Log.d("hello",property_model_data.getDetails());
                    Log.d("hello",property_model_data.getProperty_image());
                    Log.d("hello",property_model_data.getOfferedby());
                    Log.d("hello",property_model_data.getPhone_number());
                    Log.d("hello",property_model_data.getPrice());

                }
                initrecycler();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, String.valueOf(position), Toast.LENGTH_SHORT).show();
//        ImageView goneimageview = findViewById(R.id.goneimageview);
//        if(goneimageview.getVisibility() == View.GONE){
//            goneimageview.setVisibility(View.VISIBLE);
//        }
//        else{
//            goneimageview.setVisibility(View.GONE);
//        }
    }
}