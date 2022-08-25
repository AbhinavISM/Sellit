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
    private MutableLiveData<Property_model_class> tosendforedit_mutable_live;

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

    public void setDataForPropertyFragmentToEditFragment(int position) {
        Property_model_class tosendforedit = new Property_model_class(property_data_list_vm.getValue().get(position).getPhone_number(),
                property_data_list_vm.getValue().get(position).getAdress(),
                property_data_list_vm.getValue().get(position).getPrice(),
                property_data_list_vm.getValue().get(position).getDetails(),
                property_data_list_vm.getValue().get(position).getOfferedby(),
                property_data_list_vm.getValue().get(position).getProperty_image(),
                property_data_list_vm.getValue().get(position).getProperty_ID(),
                property_data_list_vm.getValue().get(position).getProperty_ID_paticular());
        tosendforedit_mutable_live = new MutableLiveData<>();
        tosendforedit_mutable_live.setValue(tosendforedit);
    }

    public LiveData<Property_model_class> getDataForPropertyFragmentToEditFragment(){
        return tosendforedit_mutable_live;
    }
}
