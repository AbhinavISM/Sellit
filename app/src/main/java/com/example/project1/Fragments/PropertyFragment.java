package com.example.project1.Fragments;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project1.data_classes.Hetero_model_for_userprofile;
import com.example.project1.ListenerInterfaces.MapInterface;
import com.example.project1.view_models.PropertyFragmentViewModel;
import com.example.project1.R;
import com.example.project1.ListenerInterfaces.add_profile_pic_interface;
import com.example.project1.RecyclerAdapters.hetero_adapter_for_userprofile;
import com.example.project1.Activities.login;
import com.example.project1.RoomDb.myPropertyEntity;
import com.example.project1.view_models.myPropertyViewModel;
import com.example.project1.ListenerInterfaces.recyclerInterface;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.List;


public class PropertyFragment extends Fragment implements add_profile_pic_interface, recyclerInterface, MapInterface {

    RecyclerView heterorecyclerView;
    hetero_adapter_for_userprofile heteroadapter;
    List<Hetero_model_for_userprofile> heteromodel;
    LinearLayoutManager heterolayoutManager;
    Uri profile_image_uri;
    PropertyFragmentViewModel propertyFragmentViewModel;
    myPropertyViewModel RoomViewModel;
    NavController navController;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_property, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        propertyFragmentViewModel = new ViewModelProvider(getActivity()).get(PropertyFragmentViewModel.class);
        propertyFragmentViewModel.initPropertyFragmentViewModel();
        RoomViewModel = new ViewModelProvider(getActivity()).get(myPropertyViewModel.class);
        init_hetero_fragment_recycler();
        propertyFragmentViewModel.get_property_data_list_vm().observe(getActivity(), new Observer<List<Hetero_model_for_userprofile>>() {
            @Override
            public void onChanged(List<Hetero_model_for_userprofile> Hetero_model_for_userprofiles) {
//                heteroadapter.notifyDataSetChanged();
            }
        });
        navController = Navigation.findNavController(getView());
        propertyFragmentViewModel.successMessage.observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null) {
                    Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                    propertyFragmentViewModel.successMessageshown();
                }
            }
        });

        RoomViewModel.getAllmyProperty().observe(getActivity(), new Observer<List<myPropertyEntity>>() {
            @Override
            public void onChanged(List<myPropertyEntity> myPropertyEntities) {
                Log.d("offline data size", String.valueOf(myPropertyEntities.size()));
                if(myPropertyEntities.size()!=0) {
                    heteroadapter.setHetero_entity_list(myPropertyEntities);
                    heteroadapter.notifyDataSetChanged();
                }
                else if(FirebaseAuth.getInstance().getCurrentUser()!=null){
                    RoomViewModel.roomKhaliHainBharDebhai();
                }
            }
        });
    }

    void init_hetero_fragment_recycler(){
        if(getView()!=null) {
            heterorecyclerView = getView().findViewById(R.id.property_fragment_rv);
            heterolayoutManager = new LinearLayoutManager(getContext());
            heterolayoutManager.setOrientation(RecyclerView.VERTICAL);
            heterorecyclerView.setLayoutManager(heterolayoutManager);
            heteroadapter = new hetero_adapter_for_userprofile(RoomViewModel.getAllmyProperty().getValue(), getContext(), this, this,this);
            heterorecyclerView.setAdapter(heteroadapter);
            heteroadapter.notifyDataSetChanged();
        }
    }

    @Override
    public void add_profile_pic_method() {
        Intent image_picker = new Intent();
        image_picker.setAction(Intent.ACTION_GET_CONTENT);
        image_picker. setType("image/*");
        startActivityForResult(image_picker,3);
    }

    @Override
    public void logout_profile_pic_method() {
        boolean logout_successful = propertyFragmentViewModel.logout_user();
        if (logout_successful) {
            Toast.makeText(getContext(),"logged out succesfully",Toast.LENGTH_LONG).show();
            RoomViewModel.allofflinedelete();
            startActivity(new Intent(getActivity(), login.class));
            getActivity().finish();
        }
        else{
            Toast.makeText(getContext(),"logout failed",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(int position) {

        propertyFragmentViewModel.setDataForPropertyFragmentToEditFragment(position);
        NavDirections action = PropertyFragmentDirections.actionPropertyFragmentToEditFragment();
        navController.navigate(action);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==3&&data!=null){
            propertyFragmentViewModel.change_profile_pic(data.getData(),System.currentTimeMillis()+"."+getExtension(data.getData()));
        }
    }

    String getExtension(Uri Auri){
        ContentResolver cr = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(Auri));
    }

    @BindingAdapter("android:loadProfilePhoto")
    public static void loadProfilePhoto(ImageView profile_image, String URL){
        Picasso.get().load(URL).fit().centerCrop().into(profile_image);
    }

    @BindingAdapter("android:loadImage")
    public static void loadImage(ImageView property_image, String URL){
        Picasso.get().load(URL).fit().centerCrop().into(property_image);
    }

    @Override
    public void startmap() {
//        NavDirections action = PropertyFragmentDirections.actionPropertyFragmentToMapsFragment();
//        navController.navigate(action);
    }
}