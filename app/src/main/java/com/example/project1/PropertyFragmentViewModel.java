package com.example.project1;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.project1.ListenerInterfaces.Propertydataloadlistener;
import com.example.project1.ListenerInterfaces.SuccessListener;
import com.example.project1.Repository.PropertyFragmentRepository;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.Map;

public class PropertyFragmentViewModel extends ViewModel implements Propertydataloadlistener, SuccessListener {

    private MutableLiveData<List<Hetero_model_for_userprofile>> property_data_list_vm;
    private PropertyFragmentRepository propertyFragmentRepository;
    private MutableLiveData<Property_model_class> tosendforedit_mutable_live;
    public MutableLiveData<String> successMessage = new MutableLiveData<String>();
    int adapterpositionforpropertyfragmnettoeditfragment;

    public LiveData<List<Hetero_model_for_userprofile>> get_property_data_list_vm(){
        return property_data_list_vm;
    }
    public void initPropertyFragmentViewModel(){
        if(property_data_list_vm == null){
            propertyFragmentRepository = PropertyFragmentRepository.getInstance(this,this);
            property_data_list_vm = propertyFragmentRepository.getProperty_data_list_repo();
        }
    }

    @Override
    public void onPropertydataloaded(List<Hetero_model_for_userprofile> fullyloadeddata) {
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

    public void update_property(Uri edit_image_uri, String image_name, Map<String,Object> map){
        propertyFragmentRepository.update_property_repo(edit_image_uri,image_name,map);
    }

    public void delete_property(String image_url, String property_ID_particular, String property_ID){
        propertyFragmentRepository.delete_property_repo(image_url,property_ID_particular,property_ID);
    }

    public void add_property(String adress, String phoneno, String details, String price,String image_name, Uri image_uri, String Lat, String Lng){
        propertyFragmentRepository.add_property_repo(adress,phoneno,details,price,image_name,image_uri, Lat, Lng);
    }

    public void setDataForPropertyFragmentToEditFragment(int position) {
        Property_model_class tosendforedit = new Property_model_class(property_data_list_vm.getValue().get(position).getPhone_number(),
                property_data_list_vm.getValue().get(position).getAdress(),
                property_data_list_vm.getValue().get(position).getPrice(),
                property_data_list_vm.getValue().get(position).getDetails(),
                property_data_list_vm.getValue().get(position).getOfferedby(),
                property_data_list_vm.getValue().get(position).getProperty_image(),
                property_data_list_vm.getValue().get(position).getProperty_ID(),
                property_data_list_vm.getValue().get(position).getProperty_ID_paticular(),
                property_data_list_vm.getValue().get(position).getLat(),
                property_data_list_vm.getValue().get(position).getLng());
        tosendforedit_mutable_live = new MutableLiveData<>();
        tosendforedit_mutable_live.setValue(tosendforedit);
        adapterpositionforpropertyfragmnettoeditfragment = position;
    }

    public LiveData<Property_model_class> getDataForPropertyFragmentToEditFragment(){
        return tosendforedit_mutable_live;
    }
    public int getAdapterpositionforpropertyfragmnettoeditfragment(){
        return adapterpositionforpropertyfragmnettoeditfragment;
    }
    @Override
    public void onSuccess(String message) {
        successMessage.postValue(message);
    }

    public void successMessageshown() {
        successMessage.postValue(null);
    }
}
