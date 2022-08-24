package com.example.project1;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class update_delete_activity extends AppCompatActivity {
    EditText edit_phoneno;
    EditText edit_adress;
    EditText edit_price;
    EditText edit_details;
    String offered_by;
    ImageView edit_Image;
    Uri edit_image_uri;
    String image_url;
    String property_ID;
    String property_ID_particular;
    Button update_button;
    Button delete_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);
        edit_phoneno = findViewById(R.id.editphoneno);
        edit_Image = findViewById(R.id.editimage);
        edit_adress = findViewById(R.id.editadress);
        edit_price = findViewById(R.id.editprice);
        edit_details = findViewById(R.id.editdetails);
        update_button = findViewById(R.id.updatebutton);
        delete_button = findViewById(R.id.deletebutton);

        Intent thisintent = getIntent();
        edit_phoneno.setText(thisintent.getStringExtra("phone_number"));
        edit_adress.setText(thisintent.getStringExtra("adress"));
        edit_price.setText((thisintent.getStringExtra("price")));
        edit_details.setText(thisintent.getStringExtra("details"));
        offered_by = thisintent.getStringExtra("offeredby");
        edit_image_uri = null;
        image_url = thisintent.getStringExtra("image");
        property_ID = thisintent.getStringExtra("property_ID");
        property_ID_particular = thisintent.getStringExtra("property_ID_particular");

        edit_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent image_picker_for_edit = new Intent();
                image_picker_for_edit.setAction(Intent.ACTION_GET_CONTENT);
                image_picker_for_edit. setType("image/*");
                startActivityForResult(image_picker_for_edit,1);
            }
        });

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String image_name = System.currentTimeMillis()+"."+getExtension(edit_image_uri);
                if(edit_image_uri != null) {
                    Log.d("image add kiya","haa");
                    FirebaseStorage.getInstance().getReference("uploads").child(image_name).putFile(edit_image_uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(update_delete_activity.this, "storage upload success", Toast.LENGTH_SHORT).show();
                                FirebaseStorage.getInstance().getReference("uploads").child(image_name).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        image_url = String.valueOf(uri);
                                        Log.d("naya url",image_url+" "+offered_by+" "+property_ID+" "+property_ID_particular);
                                        Map<String,Object> map = new HashMap<>();
                                        map.put("phone_number",edit_phoneno.getText().toString());
                                        map.put("adress",edit_adress.getText().toString());
                                        map.put("price",edit_price.getText().toString());
                                        map.put("details",edit_details.getText().toString());
                                        map.put("offeredby",offered_by);
                                        map.put("property_image",image_url);
                                        map.put("property_ID",property_ID);
                                        map.put("property_ID_particular",property_ID_particular);

                                        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("property added by this user").child(property_ID_particular).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(update_delete_activity.this, "particular succesfull", Toast.LENGTH_SHORT).show();
                                                }
                                                else{
                                                    Toast.makeText(update_delete_activity.this, "particular failed", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                        FirebaseDatabase.getInstance().getReference("property added by all users").child(property_ID).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(update_delete_activity.this, "common succesfull", Toast.LENGTH_SHORT).show();
//                                                    USE THE FOLLOWING INTENT WHEN USING IN ACTIVITY MODE
//                                                    startActivity(new Intent(update_delete_activity.this,this_user.class));
//                                                    finish();
                                                }
                                                else{
                                                    Toast.makeText(update_delete_activity.this, "common failed", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                });
                            }
                            else{
                                Toast.makeText(update_delete_activity.this, "storage upload faliure", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Log.d("image add kiya", "nahi");
                }
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseStorage.getInstance().getReferenceFromUrl(image_url).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(update_delete_activity.this, "Image deleted!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(update_delete_activity.this, "Image delete failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("property added by this user").child(property_ID_particular).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(update_delete_activity.this,"particular deleted",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(update_delete_activity.this,"particular delete failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                FirebaseDatabase.getInstance().getReference("property added by all users").child(property_ID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(update_delete_activity.this,"common deleted",Toast.LENGTH_SHORT).show();
                            //USE THE FOLLOWING INTENT WHEN WORKING IN ACTIVITY MODE
//                            startActivity(new Intent(update_delete_activity.this,this_user.class));
//                            finish();

                        }
                        else{
                            Toast.makeText(update_delete_activity.this,"common delete failed",Toast.LENGTH_SHORT).show();
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
            edit_image_uri = data.getData();
            edit_Image.setImageURI(edit_image_uri);
        }
    }

    String getExtension(Uri uri){
        if(uri!=null) {
            ContentResolver cr = getContentResolver();
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            return mime.getExtensionFromMimeType(cr.getType(uri));
        }
        else{
            return null;
        }
    }
}