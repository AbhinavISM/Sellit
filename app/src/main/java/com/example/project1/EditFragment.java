package com.example.project1;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.project1.databinding.FragmentEditBinding;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;


public class EditFragment extends Fragment {

//    EditText edit_phoneno;
//    EditText edit_adress;
//    EditText edit_price;
//    EditText edit_details;
    String offered_by;
//    ImageView edit_Image;
    Uri edit_image_uri;
    String image_url;
    String property_ID;
    String property_ID_particular;
    int adapter_position;
    FragmentEditBinding fragmentEditBinding;
//    Button update_button;
//    Button delete_button;

    PropertyFragmentViewModel propertyFragmentViewModel;
    myPropertyViewModel RoomViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentEditBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_edit,container,false);
        View view = fragmentEditBinding.getRoot();
        fragmentEditBinding.setLifecycleOwner(this);
        propertyFragmentViewModel = new ViewModelProvider(getActivity()).get(PropertyFragmentViewModel.class);
        RoomViewModel = new ViewModelProvider(getActivity()).get(myPropertyViewModel.class);
        fragmentEditBinding.setViewmodel(propertyFragmentViewModel);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        edit_phoneno = getView().findViewById(R.id.editphoneno_fragment);
//        edit_Image = getView().findViewById(R.id.editimage_fragment);
//        edit_adress = getView().findViewById(R.id.editadress_fragment);
//        edit_price = getView().findViewById(R.id.editprice_fragment);
//        edit_details = getView().findViewById(R.id.editdetails_fragment);
//        update_button = getView().findViewById(R.id.updatebutton_fragment);
//        delete_button = getView().findViewById(R.id.deletebutton_fragment);

        NavController navController = Navigation.findNavController(view);
//        Property_model_class recieved_data = EditFragmentArgs.fromBundle(getArguments()).getDataForEditFragment();
//        propertyFragmentViewModel = new ViewModelProvider(getActivity()).get(PropertyFragmentViewModel.class);
        propertyFragmentViewModel.getDataForPropertyFragmentToEditFragment().observe(getActivity(), new Observer<Property_model_class>() {
            @Override
            public void onChanged(Property_model_class recieved_data) {
//                edit_phoneno.setText(recieved_data.getPhone_number());
//                edit_adress.setText(recieved_data.getAdress());
//                edit_price.setText(recieved_data.getPrice());
//                edit_details.setText(recieved_data.getDetails());
                fragmentEditBinding.setInflateEditFragmentData(recieved_data);
                offered_by = recieved_data.getOfferedby();
                edit_image_uri = null;
                image_url = recieved_data.getProperty_image();
                property_ID = recieved_data.getProperty_ID();
                property_ID_particular = recieved_data.getProperty_ID_particular();
            }
        });
        adapter_position = propertyFragmentViewModel.getAdapterpositionforpropertyfragmnettoeditfragment();

        fragmentEditBinding.editimageFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent image_picker_for_edit = new Intent();
                image_picker_for_edit.setAction(Intent.ACTION_GET_CONTENT);
                image_picker_for_edit. setType("image/*");
                startActivityForResult(image_picker_for_edit,1);
            }
        });

        fragmentEditBinding.updatebuttonFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String image_name = System.currentTimeMillis()+"."+getExtension(edit_image_uri);
                Map<String,Object> map = new HashMap<>();
                map.put("phone_number",fragmentEditBinding.editphonenoFragment.getText().toString());
                map.put("adress",fragmentEditBinding.editadressFragment.getText().toString());
                map.put("price",fragmentEditBinding.editpriceFragment.getText().toString());
                map.put("details",fragmentEditBinding.editdetailsFragment.getText().toString());
                map.put("offeredby",offered_by);
                map.put("property_image",image_url);
                map.put("property_ID",property_ID);
                map.put("property_ID_particular",property_ID_particular);
                propertyFragmentViewModel.update_property(edit_image_uri,image_name,map);
                RoomViewModel.update_property_offline(edit_image_uri,image_name,map,adapter_position);
            }
        });

        fragmentEditBinding.deletebuttonFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                propertyFragmentViewModel.delete_property(image_url,property_ID_particular,property_ID);
                RoomViewModel.delete_property_offline(image_url,adapter_position);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&data!=null){
            edit_image_uri = data.getData();
            fragmentEditBinding.editimageFragment.setImageURI(edit_image_uri);
        }
    }

    String getExtension(Uri uri){
        if(uri!=null) {
            ContentResolver cr = getActivity().getContentResolver();
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            return mime.getExtensionFromMimeType(cr.getType(uri));
        }
        else{
            return null;
        }
    }

    @BindingAdapter("android:loadImage")
    public static void loadImage(ImageView property_image, String URL){
        Picasso.get().load(URL).into(property_image);
    }
}