package com.example.project1;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class HomeFragmentViewModel extends ViewModel implements Homedataloadlistener {

    private MutableLiveData<List<property_model_class>> home_data_list_vm;
    private HomeFragmentRepository homeFragmentRepository;

    public LiveData<List<property_model_class>> get_home_data_list_vm(){
        return home_data_list_vm;
    }

    public void initHomeFragmentViewModel() {
        if (home_data_list_vm == null) {
            homeFragmentRepository = HomeFragmentRepository.getInstance(this);
            home_data_list_vm = homeFragmentRepository.getHome_data_list_repo();
        }
    }

    @Override
    public void onHomedataloaded(List<property_model_class> fullyloadeddata) {
        home_data_list_vm.setValue(fullyloadeddata);
    }
}
