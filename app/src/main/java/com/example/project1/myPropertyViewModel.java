package com.example.project1;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class myPropertyViewModel extends AndroidViewModel {
    private myPropertyRepository mypropertyRepository;
    private LiveData<List<myPropertyEntity>> offline_property_list;

    public myPropertyViewModel(@NonNull Application application) {
        super(application);
        mypropertyRepository = new myPropertyRepository(application);
        offline_property_list = mypropertyRepository.getAllmyProperty();
    }

    public void insert_property_offline(String phoneno, String adress, String price, String details,String image_name, Uri image_uri){
        mypropertyRepository.insert_property_offline(phoneno,adress,price,details,image_name,image_uri);
    }

    public void update_property_offline(myPropertyEntity my_property_entity){
        mypropertyRepository.update_property_offline(my_property_entity);
    }

    public void delete_property_offline(myPropertyEntity my_property_entity){
        mypropertyRepository.delete_property_offline(my_property_entity);
    }

    public LiveData<List<myPropertyEntity>> getAllmyProperty(){
        return offline_property_list;
    }
}
