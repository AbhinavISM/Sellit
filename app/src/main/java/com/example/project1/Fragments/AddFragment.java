package com.example.project1.Fragments;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.project1.view_models.PropertyFragmentViewModel;
import com.example.project1.R;
import com.example.project1.view_models.myPropertyViewModel;


public class AddFragment extends Fragment {
    Uri image_uri;
    ImageView addimage;
    PropertyFragmentViewModel propertyFragmentViewModel;
    myPropertyViewModel RoomViewModel;
    String Lat;
    String Lng;
    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        propertyFragmentViewModel = new ViewModelProvider(getActivity()).get(PropertyFragmentViewModel.class);
        RoomViewModel = new ViewModelProvider(getActivity()).get(myPropertyViewModel.class);
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView addadress = getView().findViewById(R.id.add_adress_textview_fragment);
        TextView addphoneno = getView().findViewById(R.id.add_phone_no_textview_fragment);
        TextView adddetails = getView().findViewById(R.id.add_details_textview_fragment);
        TextView addprice = getView().findViewById(R.id.add_price_textview_fragment);
        addimage = getView().findViewById(R.id.Add_image_view_button_fragment);
        Button addpropbutton = getView().findViewById(R.id.Add_property_button_fragment);
        TextView OpenMapsTextView = getView().findViewById(R.id.open_maps_text_view);
        navController = Navigation.findNavController(getView());

        OpenMapsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = AddFragmentDirections.actionAddFragmentToMapsFragment("", "", "AddFragment");
                navController.navigate(action);
            }
        });

        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent image_picker = new Intent();
                image_picker.setAction(Intent.ACTION_GET_CONTENT);
                image_picker. setType("image/*");
                startActivityForResult(image_picker,1);
            }
        });

        addpropbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getArguments()!=null){
                    AddFragmentArgs addFragmentArgs = AddFragmentArgs.fromBundle(getArguments());
                    Lat = addFragmentArgs.getLatitude();
                    Lng = addFragmentArgs.getLongitude();
                }
                String adress = addadress.getText().toString();
                String phoneno = addphoneno.getText().toString();
                String details = adddetails.getText().toString();
                String price = addprice.getText().toString();
                String image_name = System.currentTimeMillis()+"."+getExtension(image_uri);
                String image_name_for_room = System.currentTimeMillis()+"."+getExtension(image_uri);
                propertyFragmentViewModel.add_property(adress,phoneno,details,price,image_name,image_uri,Lat,Lng);
                RoomViewModel.insert_property_offline(phoneno,adress,price,details,image_name_for_room,image_uri,Lat,Lng);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&data!=null){
            image_uri = data.getData();
            addimage.setImageURI(image_uri);
        }
    }

    String getExtension(Uri Turi){
        ContentResolver cr = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(Turi));
    }
}