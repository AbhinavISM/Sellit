package com.example.project1;

import static com.example.project1.Hetero_model_for_userprofile.user_property_case;

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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.List;
import java.util.Objects;

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
                            mypropertydao.insert_property_offline(new myPropertyEntity(phoneno,adress,price,details,name[0],String.valueOf(uri), user_property_case));
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
    public void insert_property_since_room_khali_tha(String phoneno, String adress, String price, String details, String url, String offered_by){
        mypropertydao.insert_property_offline(new myPropertyEntity(phoneno,adress,price,details,offered_by,url, user_property_case));

    }
    public void roomKhaliHainBharDeBhai(){
//        property_data_list_repo = new ArrayList<>();

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
//                                                    property_data_list_repo.clear();
//                                                    property_data_list_repo.add(new Hetero_model_for_userprofile(user_profile_case,username[0],useremail[0],user_profile_link[0]));
                                                    for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                                                        Property_model_class property_model_data = dataSnapshot.getValue(Property_model_class.class);
//                                                        property_data_list_repo.add(new Hetero_model_for_userprofile(property_model_data.getPhone_number(),property_model_data.getAdress(),property_model_data.getPrice(),property_model_data.getDetails(),property_model_data.getOfferedby(),property_model_data.getProperty_image(),property_model_data.getProperty_ID(),property_model_data.getProperty_ID_particular()));
                                                        insert_property_since_room_khali_tha(
                                                                property_model_data.getPhone_number(),
                                                                property_model_data.getAdress(),
                                                                property_model_data.getPrice(),
                                                                property_model_data.getDetails(),
                                                                property_model_data.getProperty_image(),
                                                                property_model_data.getOfferedby());
                                                    }
//                                                    propertydataloadlistener.onPropertydataloaded(property_data_list_repo);
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
    }
    public void update_property_offline(myPropertyEntity my_property_entity){
        mypropertydao.update_property_offline(my_property_entity);
    }
    public void delete_property_offline(String image_url, int adapter_position){

        FirebaseStorage.getInstance().getReferenceFromUrl(image_url).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
//                    Toast.makeText(getContext(), "Image deleted!", Toast.LENGTH_SHORT).show();
                }
                else{
//                    Toast.makeText(getContext(), "Image delete failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mypropertydao.delete_property_offline(offline_property_list_repo.getValue().get(adapter_position));
    }

    public void allofflinedelete(){
        int i = offline_property_list_repo.getValue().size()-1;
        while( i > 0){
            mypropertydao.delete_property_offline(offline_property_list_repo.getValue().get(i));
            i--;
        }
    }
    public LiveData<List<myPropertyEntity>> getAllmyProperty(){
        return offline_property_list_repo;
    }
}
