package com.example.project1;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class PropertyFragmentViewModel extends ViewModel {
    private MutableLiveData<List<hetero_model_for_userprofile>> property_data_list_vm;
    private PropertyFragmentRepository propertyFragmentRepository;

    public LiveData<List<hetero_model_for_userprofile>> get_property_data_list_vm(){
        return property_data_list_vm;
    }
    public void initPropertyFragmentViewModel(PropertyFragment fragment_context){
        if(property_data_list_vm == null){
            propertyFragmentRepository = PropertyFragmentRepository.getInstance(fragment_context);
            property_data_list_vm = propertyFragmentRepository.getProperty_data_list_repo();
        }
    }
}
