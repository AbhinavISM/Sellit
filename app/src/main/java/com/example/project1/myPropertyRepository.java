package com.example.project1;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.List;

public class myPropertyRepository {
    private myPropertydao mypropertydao;
    private LiveData<List<myPropertyEntity>> offline_property_list_repo;

    public myPropertyRepository(Context context){
        myPropertydb mypropertydb = myPropertydb.getInstance(context);
        mypropertydao = mypropertydb.my_property_dao();
        offline_property_list_repo = mypropertydao.getAllmyProperty();
    }

    public void insert_property_offline(String phoneno, String adress, String price, String details, String image_name, Uri image_uri){
        final String[] name = new String[1];
        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    name[0] = task.getResult().getValue().toString();
                }
            }
        });

        //UPLOAD IMAGE TO FIREBASE STORAGE
        FirebaseStorage.getInstance().getReference("uploads").child(image_name).putFile(image_uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    // TRYING TO GET URL IN COMMENTED CODE BUT IT INVOKES ON FALIURE LISTENER
                    FirebaseStorage.getInstance().getReference("uploads").child(image_name).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            mypropertydao.insert_property_offline(new myPropertyEntity(phoneno,adress,price,details,name[0],String.valueOf(uri)));
                            Log.d("hum jeet gaye", "room");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("Faliure show kr rha hain","haha");
                        }
                    });

                }
                else{
//                    Toast.makeText(getContext(), "storage upload failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void update_property_offline(myPropertyEntity my_property_entity){
        mypropertydao.update_property_offline(my_property_entity);
    }
    public void delete_property_offline(myPropertyEntity my_property_entity){
        mypropertydao.delete_property_offline(my_property_entity);
    }
    public LiveData<List<myPropertyEntity>> getAllmyProperty(){
        return offline_property_list_repo;
    }
}
