package com.example.project1;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragmentRepository {

    private List<Property_model_class> home_data_list_repo;
    private MutableLiveData<List<Property_model_class>> home_live_data_list_repo;
    public static HomeFragmentRepository instance;
    static Homedataloadlistener homedataloadlistener;

    public static HomeFragmentRepository getInstance(Homedataloadlistener context){
        homedataloadlistener = context;
        if(instance==null){
            instance =  new HomeFragmentRepository();
        }
        return instance;
    }

    public MutableLiveData<List<Property_model_class>> getHome_data_list_repo(){
        home_data_list_repo = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("property added by all users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                home_data_list_repo.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Property_model_class property_model_data = dataSnapshot.getValue(Property_model_class.class);
                    home_data_list_repo.add(property_model_data);
                }
                homedataloadlistener.onHomedataloaded(home_data_list_repo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        home_data_list_repo.add(new Property_model_class("1","1","1","1","1","https://firebasestorage.googleapis.com/v0/b/project1-2d029.appspot.com/o/uploads%2F1660569662229.jpg?alt=media&token=321ccb28-7ec3-42b0-8160-5f99de809015","-N9WeZS5SfFN-ip40Zrw","-N9WeZS80LhFSrj1NLt-"));
        home_live_data_list_repo = new MutableLiveData<>();
        home_live_data_list_repo.setValue(home_data_list_repo);
        return home_live_data_list_repo;
    }

}
