package com.example.project1;

import static com.example.project1.Hetero_model_for_userprofile.user_profile_case;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class this_user extends AppCompatActivity implements recyclerInterface, add_profile_pic_interface {
    RecyclerView heterorecyclerView;
    hetero_adapter_for_userprofile heteroadapter;
    List<Hetero_model_for_userprofile> heteromodel;
    LinearLayoutManager heterolayoutManager;
//    ImageView ProfileImage;
    Uri profile_image_uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_this_user);
//        ProfileImage = findViewById(R.id.profile_image);
        init_hetreo_data();
    }

    private void init_hetreo_data() {
        heteromodel = new ArrayList<>();

        final String[] username = new String[1];
        final String[] useremail = new String[1];
        final String[] user_profile_link = new String[1];
        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    username[0] = Objects.requireNonNull(task.getResult().getValue()).toString();
                    Log.d("name : ",String.valueOf(username[0]));

                    // ab email nikal
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("email").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if(task.isSuccessful()){
                                useremail[0] = Objects.requireNonNull(task.getResult().getValue(String.class));
                                Log.d("email : ",useremail[0]);
                                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("profile_pic_link").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        if(task.isSuccessful()){
                                            user_profile_link[0] = task.getResult().getValue(String.class);
                                            if(user_profile_link[0]==null){
                                                user_profile_link[0] = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQmzsiq2kL7EYn1TofQ1k8lLzdZhN5eyWjINA&usqp=CAU";
                                            }
                                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("property added by this user").addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    heteromodel.clear();
                                                    heteromodel.add(new Hetero_model_for_userprofile(user_profile_case,username[0],useremail[0],user_profile_link[0]));
                                                    for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                                                        Property_model_class property_model_data = dataSnapshot.getValue(Property_model_class.class);
                                                        heteromodel.add(new Hetero_model_for_userprofile(property_model_data.getPhone_number(),property_model_data.getAdress(),property_model_data.getPrice(),property_model_data.getDetails(),property_model_data.getOfferedby(),property_model_data.getProperty_image(),property_model_data.getProperty_ID(),property_model_data.getProperty_ID_particular()));
                                                    }
                                                    init_hetero_recycler();
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                        }
                                    }
                                });

                            }
                        }
                    });
                }
            }
        });

//        int delay = 1000; // number of milliseconds to sleep
//
//        long start = System.currentTimeMillis();
//        while(start >= System.currentTimeMillis() - delay){
//
//        }

//        String dekh = username[0];
//        Log.d("dekh ", String.valueOf(dekh));
        //fetching current useremail for profile case(need to do only once)




//        heteromodel.add(new Hetero_model_for_userprofile(user_profile_case,"naam","naam@naam.com","https://firebasestorage.googleapis.com/v0/b/project1-2d029.appspot.com/o/uploads%2F1660458189951.jpg?alt=media&token=46e81358-0a2f-4c51-a28d-9c60a40a2053"));
//        heteromodel.add(new Hetero_model_for_userprofile("8456434648","bfgisuegy","93582","rhgsjegrs0","maine daala hain","https://firebasestorage.googleapis.com/v0/b/project1-2d029.appspot.com/o/uploads%2F1660458189951.jpg?alt=media&token=46e81358-0a2f-4c51-a28d-9c60a40a2053","NO_ID","NO_ID"));
    }

    private void init_hetero_recycler(){
        heterorecyclerView = findViewById(R.id.hetero_recycler_xml);
        heterolayoutManager = new LinearLayoutManager(this);
        heterolayoutManager.setOrientation(RecyclerView.VERTICAL);
        heterorecyclerView.setLayoutManager(heterolayoutManager);
        heteroadapter = new hetero_adapter_for_userprofile(heteromodel,this,this,this);
        heterorecyclerView.setAdapter(heteroadapter);
        heteroadapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this,"clicked",Toast.LENGTH_SHORT).show();
        Intent edit_prop_intent = new Intent(this_user.this,update_delete_activity.class);
        edit_prop_intent.putExtra("phone_number",heteromodel.get(position).getPhone_number());
        edit_prop_intent.putExtra("adress",heteromodel.get(position).getAdress());
        edit_prop_intent.putExtra("price",heteromodel.get(position).getPrice());
        edit_prop_intent.putExtra("details",heteromodel.get(position).getDetails());
        edit_prop_intent.putExtra("offeredby",heteromodel.get(position).getOfferedby());
        edit_prop_intent.putExtra("image",heteromodel.get(position).getProperty_image());
        edit_prop_intent.putExtra("property_ID",heteromodel.get(position).getProperty_ID());
        edit_prop_intent.putExtra("property_ID_particular",heteromodel.get(position).getProperty_ID_paticular());
        edit_prop_intent.putExtra("adapter_pos",position);
        startActivity(edit_prop_intent);
    }

    @Override
    public void add_profile_pic_method() {
        Intent image_picker = new Intent();
        image_picker.setAction(Intent.ACTION_GET_CONTENT);
        image_picker. setType("image/*");
        startActivityForResult(image_picker,2);
    }

    @Override
    public void logout_profile_pic_method() {
        FirebaseAuth.getInstance().signOut();
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Toast.makeText(this_user.this,"logged out succesfully",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this_user.this,login.class));
            finish();
        }
        else{
            Toast.makeText(this_user.this,"logout failed",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2&&resultCode==RESULT_OK&&data!=null){
            profile_image_uri = data.getData();
//            ProfileImage.setImageURI(profile_image_uri);
            String image_name = System.currentTimeMillis()+"."+getExtension(profile_image_uri);
            FirebaseStorage.getInstance().getReference("profile_pics").child(image_name).putFile(profile_image_uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if(task.isSuccessful()){
                        FirebaseStorage.getInstance().getReference("profile_pics").child(image_name).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("profile_pic_link").setValue(uri.toString());
                                Toast.makeText(this_user.this, "succesfully changed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        }
    }
    String getExtension(Uri Auri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(Auri));
    }
}