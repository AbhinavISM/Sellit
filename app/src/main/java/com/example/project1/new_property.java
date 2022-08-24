package com.example.project1;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

public class new_property extends AppCompatActivity {

    Uri image_uri;
    ImageView addimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_property);
//        following line no. 39 is giving error

        TextView addadress = findViewById(R.id.add_adress_textview);
        TextView addphoneno = findViewById(R.id.add_phone_no_textview);
        TextView adddetails = findViewById(R.id.add_details_textview);
        TextView addprice = findViewById(R.id.add_price_textview);
        addimage = findViewById(R.id.Add_image_view_button);
        Button addpropbutton = findViewById(R.id.Add_property_button);

//        DatabaseReference dataref = FirebaseDatabase.getInstance().getReference("Users");
//        StorageReference storageref = FirebaseStorage.getInstance().getReference();
        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent image_picker = new Intent();
                 image_picker.setAction(Intent.ACTION_GET_CONTENT);
                 image_picker. setType("image/*");
                 startActivityForResult(image_picker,1);
            }
        });

        addpropbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] name = new String[1];
                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(task.isSuccessful()){
                            name[0] = task.getResult().getValue().toString();
                        }
                    }
                });
                String adress = addadress.getText().toString();
                String phoneno = addphoneno.getText().toString();
                String details = adddetails.getText().toString();
                String price = addprice.getText().toString();



                //UPLOAD IMAGE TO FIREBASE STORAGE
                String image_name = System.currentTimeMillis()+"."+getExtension(image_uri);
                FirebaseStorage.getInstance().getReference("uploads").child(image_name).putFile(image_uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful()){
                           // TRYING TO GET URL IN COMMENTED CODE BUT IT INVOKES ON FALIURE LISTENER
                            FirebaseStorage.getInstance().getReference("uploads").child(image_name).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Log.d("Success show kr rha hain", String.valueOf(uri));
                                    String property_ID = FirebaseDatabase.getInstance().getReference("property added by all users").push().getKey();
                                    String property_ID_particular = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("property added by this user").push().getKey();
                                    new_property_info property_object = new new_property_info(phoneno,adress,price,details, name[0],String.valueOf(uri),property_ID,property_ID_particular);
                                    FirebaseDatabase.getInstance().getReference("property added by all users").child(property_ID).setValue(property_object);
                                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("property added by this user").child(property_ID_particular).setValue(property_object).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(new_property.this, "succesfully uploaded", Toast.LENGTH_SHORT).show();
                                                //the next line causes problem of showing whole outdated list once again along with whole updated list
//                                                adapterofprop.notifyDataSetChanged();
                                                addimage.setImageResource(R.drawable.ic_baseline_add_a_photo_24);
                                                addadress.setText("");
                                                addphoneno.setText("");
                                                adddetails.setText("");
                                                addprice.setText("");
                                                startActivity(new Intent(new_property.this,recycler_activity.class));
                                                finish();
                                            }
                                            else{
                                                Toast.makeText(new_property.this, "fireabse upload failed", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("Faliure show kr rha hain","haha");
                                }
                            });

                        }
                        else{
                            Toast.makeText(new_property.this, "storage upload failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==RESULT_OK&&data!=null){
            image_uri = data.getData();
            addimage.setImageURI(image_uri);
        }
    }

    String getExtension(Uri Turi){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(Turi));
    }
}