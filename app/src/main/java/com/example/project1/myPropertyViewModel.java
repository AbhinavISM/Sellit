package com.example.project1;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.Map;

public class myPropertyViewModel extends AndroidViewModel {
    private myPropertyRepository mypropertyRepository;
    private LiveData<List<myPropertyEntity>> offline_property_list;

    public myPropertyViewModel(@NonNull Application application) {
        super(application);
        mypropertyRepository = myPropertyRepository.init(application);
        offline_property_list = mypropertyRepository.getAllmyProperty();
    }

    public void insert_property_offline(String phoneno, String adress, String price, String details,String image_name, Uri image_uri, String Lat, String Lng){
        mypropertyRepository.insert_property_offline(phoneno,adress,price,details,image_name,image_uri, Lat, Lng);
    }

    public void update_property_offline(Uri edit_image_uri, String image_name, Map<String,Object> map, int adapter_position){
        mypropertyRepository.update_property_offline(edit_image_uri, image_name, map, adapter_position);
    }

    public void delete_property_offline(String image_url, int adapter_position){
        mypropertyRepository.delete_property_offline(image_url,adapter_position);
    }

    public void roomKhaliHainBharDebhai(){
        mypropertyRepository.roomKhaliHainBharDeBhai();
    }

    public void allofflinedelete(){
        mypropertyRepository.allofflinedelete();
    }

    public LiveData<List<myPropertyEntity>> getAllmyProperty(){
        return offline_property_list;
    }
}
