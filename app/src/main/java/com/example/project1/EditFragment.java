package com.example.project1;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;


public class EditFragment extends Fragment {

    EditText edit_phoneno;
    EditText edit_adress;
    EditText edit_price;
    EditText edit_details;
    String offered_by;
    ImageView edit_Image;
    Uri edit_image_uri;
    String image_url;
    String property_ID;
    String property_ID_particular;
    int adapter_position;

    Button update_button;
    Button delete_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edit_phoneno = getView().findViewById(R.id.editphoneno_fragment);
        edit_Image = getView().findViewById(R.id.editimage_fragment);
        edit_adress = getView().findViewById(R.id.editadress_fragment);
        edit_price = getView().findViewById(R.id.editprice_fragment);
        edit_details = getView().findViewById(R.id.editdetails_fragment);
        update_button = getView().findViewById(R.id.updatebutton_fragment);
        delete_button = getView().findViewById(R.id.deletebutton_fragment);

        NavController navController = Navigation.findNavController(view);
        property_model_class recieved_data = EditFragmentArgs.fromBundle(getArguments()).getDataForEditFragment();

        edit_phoneno.setText(recieved_data.getPhone_number());
        edit_adress.setText(recieved_data.getAdress());
        edit_price.setText(recieved_data.getPrice());
        edit_details.setText(recieved_data.getDetails());
        offered_by = recieved_data.getOfferedby();
        edit_image_uri = null;
        image_url = recieved_data.getProperty_image();
        property_ID = recieved_data.getProperty_ID();
        property_ID_particular = recieved_data.getProperty_ID_particular();

        edit_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent image_picker_for_edit = new Intent();
                image_picker_for_edit.setAction(Intent.ACTION_GET_CONTENT);
                image_picker_for_edit. setType("image/*");
                startActivityForResult(image_picker_for_edit,1);
            }
        });

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String image_name = System.currentTimeMillis()+"."+getExtension(edit_image_uri);
                if(edit_image_uri != null) {
                    Log.d("image add kiya","haa");
                    FirebaseStorage.getInstance().getReference("uploads").child(image_name).putFile(edit_image_uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "storage upload success", Toast.LENGTH_SHORT).show();
                                FirebaseStorage.getInstance().getReference("uploads").child(image_name).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        image_url = String.valueOf(uri);
                                        Log.d("naya url",image_url+" "+offered_by+" "+property_ID+" "+property_ID_particular);
                                        Map<String,Object> map = new HashMap<>();
                                        map.put("phone_number",edit_phoneno.getText().toString());
                                        map.put("adress",edit_adress.getText().toString());
                                        map.put("price",edit_price.getText().toString());
                                        map.put("details",edit_details.getText().toString());
                                        map.put("offeredby",offered_by);
                                        map.put("property_image",image_url);
                                        map.put("property_ID",property_ID);
                                        map.put("property_ID_particular",property_ID_particular);

                                        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("property added by this user").child(property_ID_particular).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(getContext(), "particular succesfull", Toast.LENGTH_SHORT).show();
                                                }
                                                else{
                                                    Toast.makeText(getContext(), "particular failed", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                        FirebaseDatabase.getInstance().getReference("property added by all users").child(property_ID).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(getContext(), "common succesfull", Toast.LENGTH_SHORT).show();
//                                                    USE THE FOLLOWING INTENT WHEN USING IN ACTIVITY MODE
//                                                    startActivity(new Intent(update_delete_activity.this,this_user.class));
//                                                    finish();
                                                }
                                                else{
                                                    Toast.makeText(getContext(), "common failed", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                });
                            }
                            else{
                                Toast.makeText(getContext(), "storage upload faliure", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Log.d("image add kiya", "nahi");
                }
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseStorage.getInstance().getReferenceFromUrl(image_url).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getContext(), "Image deleted!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getContext(), "Image delete failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("property added by this user").child(property_ID_particular).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getContext(),"particular deleted",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getContext(),"particular delete failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                FirebaseDatabase.getInstance().getReference("property added by all users").child(property_ID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getContext(),"common deleted",Toast.LENGTH_SHORT).show();
                            //USE THE FOLLOWING INTENT WHEN WORKING IN ACTIVITY MODE
//                            startActivity(new Intent(update_delete_activity.this,this_user.class));
//                            finish();

                        }
                        else{
                            Toast.makeText(getContext(),"common delete failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&data!=null){
            edit_image_uri = data.getData();
            edit_Image.setImageURI(edit_image_uri);
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
}