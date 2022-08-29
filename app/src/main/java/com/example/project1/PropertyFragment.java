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

import com.squareup.picasso.Picasso;

import java.util.List;


public class PropertyFragment extends Fragment implements add_profile_pic_interface, recyclerInterface {

    RecyclerView heterorecyclerView;
    hetero_adapter_for_userprofile heteroadapter;
    List<Hetero_model_for_userprofile> heteromodel;
    LinearLayoutManager heterolayoutManager;
    Uri profile_image_uri;
    PropertyFragmentViewModel propertyFragmentViewModel;

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
        init_hetero_fragment_recycler();
        propertyFragmentViewModel.get_property_data_list_vm().observe(getActivity(), new Observer<List<Hetero_model_for_userprofile>>() {
            @Override
            public void onChanged(List<Hetero_model_for_userprofile> Hetero_model_for_userprofiles) {
                heteroadapter.notifyDataSetChanged();
            }
        });

        propertyFragmentViewModel.successMessage.observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null) {
                    Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                    propertyFragmentViewModel.successMessageshown();
                }
            }
        });
    }//

    void init_hetero_fragment_data() {
//        heteromodel = new ArrayList<>();
//
//        final String[] username = new String[1];
//        final String[] useremail = new String[1];
//        final String[] user_profile_link = new String[1];
//        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if(task.isSuccessful()){
//                    username[0] = Objects.requireNonNull(task.getResult().getValue()).toString();
//                    Log.d("name : ",String.valueOf(username[0]));
//
//                    // ab email nikal
//                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("email").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<DataSnapshot> task) {
//                            if(task.isSuccessful()){
//                                useremail[0] = Objects.requireNonNull(task.getResult().getValue(String.class));
//                                Log.d("email : ",useremail[0]);
//                                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("profile_pic_link").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
//                                        if(task.isSuccessful()){
//                                            user_profile_link[0] = task.getResult().getValue(String.class);
//                                            if(user_profile_link[0]==null){
//                                                user_profile_link[0] = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQmzsiq2kL7EYn1TofQ1k8lLzdZhN5eyWjINA&usqp=CAU";
//                                            }
//                                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("property added by this user").addValueEventListener(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                                    heteromodel.clear();
//                                                    heteromodel.add(new Hetero_model_for_userprofile(user_profile_case,username[0],useremail[0],user_profile_link[0]));
//                                                    for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
//                                                        Property_model_class property_model_data = dataSnapshot.getValue(Property_model_class.class);
//                                                        heteromodel.add(new Hetero_model_for_userprofile(property_model_data.getPhone_number(),property_model_data.getAdress(),property_model_data.getPrice(),property_model_data.getDetails(),property_model_data.getOfferedby(),property_model_data.getProperty_image(),property_model_data.getProperty_ID(),property_model_data.getProperty_ID_particular()));
//                                                    }
//                                                    init_hetero_fragment_recycler();
//                                                }
//
//                                                @Override
//                                                public void onCancelled(@NonNull DatabaseError error) {
//
//                                                }
//                                            });
//                                        }
//                                    }
//                                });
//
//                            }
//                        }
//                    });
//                }
//            }
//        });

//        int delay = 1000; // number of milliseconds to sleep
//
//        long start = System.currentTimeMillis();
//        while(start >= System.currentTimeMillis() - delay){
//
//        }

//        String dekh = username[0];
//        Log.d("dekh ", String.valueOf(dekh));
        //fetching current useremail for profile case(need to do only once)




//        heteromodel.add(new Hetero_model_for_userprofile(user_profile_case,"naam","naam@naam.com","https://firebasestorage.googleapis.com/v0/b/project1-2d029.appspot.com/o/uploads%2F1660458189951.jpg?alt=media&token=46e81358-0a2f-4c51-a28d-9c60a40a2053"));
//        heteromodel.add(new Hetero_model_for_userprofile("8456434648","bfgisuegy","93582","rhgsjegrs0","maine daala hain","https://firebasestorage.googleapis.com/v0/b/project1-2d029.appspot.com/o/uploads%2F1660458189951.jpg?alt=media&token=46e81358-0a2f-4c51-a28d-9c60a40a2053","NO_ID","NO_ID"));
    }

    void init_hetero_fragment_recycler(){
        if(getView()!=null) {
            heterorecyclerView = getView().findViewById(R.id.property_fragment_rv);
            heterolayoutManager = new LinearLayoutManager(getContext());
            heterolayoutManager.setOrientation(RecyclerView.VERTICAL);
            heterorecyclerView.setLayoutManager(heterolayoutManager);
            heteroadapter = new hetero_adapter_for_userprofile(propertyFragmentViewModel.get_property_data_list_vm().getValue(), getContext(), this, this);
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
            startActivity(new Intent(getActivity(),login.class));
            getActivity().finish();
        }
        else{
            Toast.makeText(getContext(),"logout failed",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(int position) {
        //UNCOMMENT THIS WHEN IN ACTIVITY MODE

//        Toast.makeText(getContext(),"clicked",Toast.LENGTH_SHORT).show();
//        Intent edit_prop_intent = new Intent(getActivity(),update_delete_activity.class);
//        edit_prop_intent.putExtra("phone_number",heteromodel.get(position).getPhone_number());
//        edit_prop_intent.putExtra("adress",heteromodel.get(position).getAdress());
//        edit_prop_intent.putExtra("price",heteromodel.get(position).getPrice());
//        edit_prop_intent.putExtra("details",heteromodel.get(position).getDetails());
//        edit_prop_intent.putExtra("offeredby",heteromodel.get(position).getOfferedby());
//        edit_prop_intent.putExtra("image",heteromodel.get(position).getProperty_image());
//        edit_prop_intent.putExtra("property_ID",heteromodel.get(position).getProperty_ID());
//        edit_prop_intent.putExtra("property_ID_particular",heteromodel.get(position).getProperty_ID_paticular());
//        edit_prop_intent.putExtra("adapter_pos",position);
//        startActivity(edit_prop_intent);

        NavController navController = Navigation.findNavController(getView());
        propertyFragmentViewModel.setDataForPropertyFragmentToEditFragment(position);
//        Property_model_class tosendforedit = new Property_model_class(propertyFragmentViewModel.get_property_data_list_vm().getValue().get(position).getPhone_number(),
//                propertyFragmentViewModel.get_property_data_list_vm().getValue().get(position).getAdress(),
//                propertyFragmentViewModel.get_property_data_list_vm().getValue().get(position).getPrice(),
//                propertyFragmentViewModel.get_property_data_list_vm().getValue().get(position).getDetails(),
//                propertyFragmentViewModel.get_property_data_list_vm().getValue().get(position).getOfferedby(),
//                propertyFragmentViewModel.get_property_data_list_vm().getValue().get(position).getProperty_image(),
//                propertyFragmentViewModel.get_property_data_list_vm().getValue().get(position).getProperty_ID(),
//                propertyFragmentViewModel.get_property_data_list_vm().getValue().get(position).getProperty_ID_paticular());
        NavDirections action = PropertyFragmentDirections.actionPropertyFragmentToEditFragment();
        navController.navigate(action);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==3&&data!=null){
            propertyFragmentViewModel.change_profile_pic(data.getData(),System.currentTimeMillis()+"."+getExtension(data.getData()));
//            profile_image_uri = data.getData();
//            ProfileImage.setImageURI(profile_image_uri);
//            String image_name = System.currentTimeMillis()+"."+getExtension(profile_image_uri);
//            FirebaseStorage.getInstance().getReference("profile_pics").child(image_name).putFile(profile_image_uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                    if(task.isSuccessful()){
//                        FirebaseStorage.getInstance().getReference("profile_pics").child(image_name).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @Override
//                            public void onSuccess(Uri uri) {
//                                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("profile_pic_link").setValue(uri.toString());
//                                Toast.makeText(getContext(), "succesfully changed", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//                }
//            });


        }
    }

    String getExtension(Uri Auri){
        ContentResolver cr = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(Auri));
    }

    @BindingAdapter("android:loadProfilePhoto")
    public static void loadProfilePhoto(ImageView profile_image, String URL){
        Picasso.get().load(URL).into(profile_image);
    }

    @BindingAdapter("android:loadImage")
    public static void loadImage(ImageView property_image, String URL){
        Picasso.get().load(URL).into(property_image);
    }
}