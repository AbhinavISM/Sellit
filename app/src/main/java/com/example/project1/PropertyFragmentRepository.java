package com.example.project1;

import static com.example.project1.Hetero_model_for_userprofile.user_profile_case;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PropertyFragmentRepository {

    MutableLiveData<List<Hetero_model_for_userprofile>> property_live_data_list_repo;
    List<Hetero_model_for_userprofile> property_data_list_repo;
    public static PropertyFragmentRepository instance;
    static Propertydataloadlistener propertydataloadlistener;
    static SuccessListener successListener;

    public static PropertyFragmentRepository getInstance(Propertydataloadlistener context,SuccessListener listener){
        propertydataloadlistener = context;
        successListener = listener;
        if(instance==null){
            instance = new PropertyFragmentRepository();
        }
        return instance;
    }

    public MutableLiveData<List<Hetero_model_for_userprofile>> getProperty_data_list_repo(){
        property_data_list_repo = new ArrayList<>();

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
                                                    property_data_list_repo.clear();
                                                    property_data_list_repo.add(new Hetero_model_for_userprofile(user_profile_case,username[0],useremail[0],user_profile_link[0]));
                                                    for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                                                        Property_model_class property_model_data = dataSnapshot.getValue(Property_model_class.class);
                                                        property_data_list_repo.add(new Hetero_model_for_userprofile(property_model_data.getPhone_number(),property_model_data.getAdress(),property_model_data.getPrice(),property_model_data.getDetails(),property_model_data.getOfferedby(),property_model_data.getProperty_image(),property_model_data.getProperty_ID(),property_model_data.getProperty_ID_particular()));
                                                    }
                                                    propertydataloadlistener.onPropertydataloaded(property_data_list_repo);
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
        property_live_data_list_repo = new MutableLiveData<>();
        property_live_data_list_repo.setValue(property_data_list_repo);
        return property_live_data_list_repo;
    }

    public void change_profile_pic_repo(Uri profile_image_uri, String image_name){
            FirebaseStorage.getInstance().getReference("profile_pics").child(image_name).putFile(profile_image_uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if(task.isSuccessful()){
                        FirebaseStorage.getInstance().getReference("profile_pics").child(image_name).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("profile_pic_link").setValue(uri.toString());
//                                Toast.makeText(, "succesfully changed", Toast.LENGTH_SHORT).show();
                                successListener.onSuccess("succesfully changed");
                            }
                        });
                    }
                }
            });
    }
    public void update_property_repo(Uri edit_image_uri, String image_name, Map<String,Object> map){

        if(edit_image_uri != null) {
            Log.d("image add kiya","haa");
            FirebaseStorage.getInstance().getReference("uploads").child(image_name).putFile(edit_image_uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
//                        Toast.makeText(getContext(), "storage upload success", Toast.LENGTH_SHORT).show();
                        FirebaseStorage.getInstance().getReference("uploads").child(image_name).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                map.put("property_image", String.valueOf(uri));
//                                Log.d("naya url",image_url+" "+offered_by+" "+property_ID+" "+property_ID_particular);

                                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("property added by this user").child(String.valueOf(map.getOrDefault("property_ID_particular",""))).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
//                                            Toast.makeText(getContext(), "particular succesfull", Toast.LENGTH_SHORT).show();
                                        }
                                        else{
//                                            Toast.makeText(getContext(), "particular failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                FirebaseDatabase.getInstance().getReference("property added by all users").child(String.valueOf(map.getOrDefault("property_ID",""))).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
//                                            Toast.makeText(getContext(), "common succesfull", Toast.LENGTH_SHORT).show();
//                                                    USE THE FOLLOWING INTENT WHEN USING IN ACTIVITY MODE
//                                                    startActivity(new Intent(update_delete_activity.this,this_user.class));
//                                                    finish();
                                        }
                                        else{
//                                            Toast.makeText(getContext(), "common failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        });
                    }
                    else{
//                        Toast.makeText(getContext(), "storage upload faliure", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else {
            Log.d("image add kiya", "nahi");
            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("property added by this user").child(String.valueOf(map.getOrDefault("property_ID_particular",""))).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
//                                           Toast.makeText(getContext(), "particular succesfull", Toast.LENGTH_SHORT).show();
                    }
                    else{
//                                           Toast.makeText(getContext(), "particular failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            FirebaseDatabase.getInstance().getReference("property added by all users").child(String.valueOf(map.getOrDefault("property_ID",""))).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
//                                           Toast.makeText(getContext(), "common succesfull", Toast.LENGTH_SHORT).show();
//                                                   USE THE FOLLOWING INTENT WHEN USING IN ACTIVITY MODE
//                                                   startActivity(new Intent(update_delete_activity.this,this_user.class));
//                                                   finish();
                    }
                    else{
//                                           Toast.makeText(getContext(), "common failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void delete_property_repo(String image_url, String property_ID_particular, String property_ID){
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
        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("property added by this user").child(property_ID_particular).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
//                    Toast.makeText(getContext(),"particular deleted",Toast.LENGTH_SHORT).show();
                }
                else{
//                    Toast.makeText(getContext(),"particular delete failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
        FirebaseDatabase.getInstance().getReference("property added by all users").child(property_ID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
//                    Toast.makeText(getContext(),"common deleted",Toast.LENGTH_SHORT).show();
                    //USE THE FOLLOWING INTENT WHEN WORKING IN ACTIVITY MODE
//                            startActivity(new Intent(update_delete_activity.this,this_user.class));
//                            finish();

                }
                else{
//                    Toast.makeText(getContext(),"common delete failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void add_property_repo(String adress, String phoneno, String details, String price,String image_name, Uri image_uri){

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
                            Log.d("Success show kr rha hain", String.valueOf(uri));
                            String property_ID = FirebaseDatabase.getInstance().getReference("property added by all users").push().getKey();
                            Log.d("line 1", "success");
                            String property_ID_particular = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("property added by this user").push().getKey();
                            Log.d("line 2", "success");
                            new_property_info property_object = new new_property_info(phoneno,adress,price,details, name[0],String.valueOf(uri),property_ID,property_ID_particular);
                            Log.d("line 3", "success");
                            FirebaseDatabase.getInstance().getReference("property added by all users").child(property_ID).setValue(property_object);
                            Log.d("line 4", "success");
                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("property added by this user").child(property_ID_particular).setValue(property_object).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Log.d("line 5", "success");
                                    if(task.isSuccessful()){
                                        Log.d("full success", "OOOOOO");
//                                        Toast.makeText(getContext(), "succesfully uploaded", Toast.LENGTH_SHORT).show();
                                        //the next line causes problem of showing whole outdated list once again along with whole updated list
//                                                adapterofprop.notifyDataSetChanged();
//                                        addimage.setImageResource(R.drawable.ic_baseline_add_a_photo_24);
//                                        addadress.setText("");
//                                        addphoneno.setText("");
//                                        adddetails.setText("");
//                                        addprice.setText("");

                                    }
                                    else{
//                                        Toast.makeText(getContext(), "fireabse upload failed", Toast.LENGTH_SHORT).show();
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
//                    Toast.makeText(getContext(), "storage upload failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}

interface SuccessListener {
    void onSuccess(String message);
}
