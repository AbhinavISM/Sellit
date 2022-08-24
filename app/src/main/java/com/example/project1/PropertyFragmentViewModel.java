package com.example.project1;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class PropertyFragmentViewModel extends ViewModel implements Propertydataloadlistener{
    private MutableLiveData<List<hetero_model_for_userprofile>> property_data_list_vm;
    private PropertyFragmentRepository propertyFragmentRepository;

    public LiveData<List<hetero_model_for_userprofile>> get_property_data_list_vm(){
        return property_data_list_vm;
    }
    public void initPropertyFragmentViewModel(){
        if(property_data_list_vm == null){
            propertyFragmentRepository = PropertyFragmentRepository.getInstance(this);
            property_data_list_vm = propertyFragmentRepository.getProperty_data_list_repo();
        }
    }

    @Override
    public void onPropertydataloaded(List<hetero_model_for_userprofile> fullyloadeddata) {
        property_data_list_vm.setValue(fullyloadeddata);
    }

    public boolean logout_user(){
        FirebaseAuth.getInstance().signOut();
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            return true;
        }
        else{
            return false;
        }
    }

    public void change_profile_pic(Uri profile_image_uri, String image_name){
        propertyFragmentRepository.change_profile_pic_repo(profile_image_uri,image_name);
    }

}
