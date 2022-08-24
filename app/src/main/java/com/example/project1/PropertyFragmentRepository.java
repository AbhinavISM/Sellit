package com.example.project1;

import static com.example.project1.hetero_model_for_userprofile.user_profile_case;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PropertyFragmentRepository {

    MutableLiveData<List<hetero_model_for_userprofile>> property_live_data_list_repo;
    List<hetero_model_for_userprofile> property_data_list_repo;
    public static PropertyFragmentRepository instance;
    static Propertydataloadlistener propertydataloadlistener;

    public static PropertyFragmentRepository getInstance(Propertydataloadlistener context){
        propertydataloadlistener = context;
        if(instance==null){
            instance = new PropertyFragmentRepository();
        }
        return instance;
    }

    public MutableLiveData<List<hetero_model_for_userprofile>> getProperty_data_list_repo(){
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
                                                    property_data_list_repo.add(new hetero_model_for_userprofile(user_profile_case,username[0],useremail[0],user_profile_link[0]));
                                                    for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                                                        property_model_class property_model_data = dataSnapshot.getValue(property_model_class.class);
                                                        property_data_list_repo.add(new hetero_model_for_userprofile(property_model_data.getPhone_number(),property_model_data.getAdress(),property_model_data.getPrice(),property_model_data.getDetails(),property_model_data.getOfferedby(),property_model_data.getProperty_image(),property_model_data.getProperty_ID(),property_model_data.getProperty_ID_particular()));
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
}
