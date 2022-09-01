package com.example.project1;

import static com.example.project1.Hetero_model_for_userprofile.user_profile_case;
import static com.example.project1.Hetero_model_for_userprofile.user_property_case;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project1.databinding.PropertyLayoutBinding;
import com.example.project1.databinding.UserProfileBinding;

import java.util.List;

public class hetero_adapter_for_userprofile extends RecyclerView.Adapter{
//
    List<myPropertyEntity> hetero_entity_list;
//    List<Hetero_model_for_userprofile> hetero_list;
    Context context;
    recyclerInterface recycler_interafce_for_profile;
    add_profile_pic_interface profile_pic_interface;

    @Override
    public int getItemViewType(int position) {
//        return hetero_list.get(position).getViewtype();
        if(hetero_entity_list == null){
            return user_property_case;
        }
        else{
            return  hetero_entity_list.get(position).getViewtype_room();
        }
    }

    public hetero_adapter_for_userprofile(List<myPropertyEntity> hetero_entity_list, Context context, recyclerInterface reycler_interafce_for_profile, add_profile_pic_interface profile_pic_interface) {
//        this.hetero_list = hetero_list;
        this.hetero_entity_list = hetero_entity_list;
        this.context = context;
        this.recycler_interafce_for_profile = reycler_interafce_for_profile;
        this.profile_pic_interface = profile_pic_interface;
    }

    public void setHetero_entity_list(List<myPropertyEntity> hetero_entity_list){
        this.hetero_entity_list = hetero_entity_list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch(viewType){
            case user_profile_case:
                LayoutInflater layoutInflater1 = LayoutInflater.from(context);
                UserProfileBinding userProfileBinding = UserProfileBinding.inflate(layoutInflater1,parent,false);
                return new user_profile_case_ViewHolder(userProfileBinding);
            case user_property_case:
                LayoutInflater layoutInflater2 = LayoutInflater.from(context);
                PropertyLayoutBinding propertyLayoutBinding1 = PropertyLayoutBinding.inflate(layoutInflater2,parent,false);
                return new user_property_case_ViewHolder(propertyLayoutBinding1);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (hetero_entity_list.get(position).getViewtype_room()){
            case user_profile_case:
                String profile_image_data = hetero_entity_list.get(position).getProfile_image_room();
                String profile_username_fromfirebase_data = hetero_entity_list.get(position).getProfile_username_room();
                String profile_useremail_fromfirebase_data = hetero_entity_list.get(position).getProfile_useremail_room();
                ((user_profile_case_ViewHolder)holder).userProfileBinding.setProfileImageUrl(profile_image_data);
                ((user_profile_case_ViewHolder)holder).userProfileBinding.setProfileEmail(profile_useremail_fromfirebase_data);
                ((user_profile_case_ViewHolder)holder).userProfileBinding.setProfileUsername(profile_username_fromfirebase_data);
                break;
            case user_property_case:
//                String adressdata = hetero_list.get(position).getAdress();
//                String phnodata = hetero_list.get(position).getPhone_number();
//                Log.d("phone no print kiya hain, dekh adress siiciiv mil rha hain kya",phnodata);
//                String pricedata = hetero_list.get(position).getPrice();
//                String detailsdata = hetero_list.get(position).getDetails();
//                String offerdbydata = hetero_list.get(position).getOfferedby();
//                String imagedata = hetero_list.get(position).getProperty_image();

                String adressdata = hetero_entity_list.get(position).getAdress();
                String phnodata = hetero_entity_list.get(position).getPhone_number();
                Log.d("phone no print kiya hain, dekh adress siiciiv mil rha hain kya",phnodata);
                String pricedata = hetero_entity_list.get(position).getPrice();
                String detailsdata = hetero_entity_list.get(position).getDetails();
                String offerdbydata = hetero_entity_list.get(position).getOfferedby();
                String imagedata = hetero_entity_list.get(position).getProperty_image();
                ((user_property_case_ViewHolder)holder).propertyLayoutBinding1.setPropertyModelClassData(
                        new Property_model_class(phnodata, adressdata,pricedata,detailsdata,offerdbydata,imagedata,"dummy", "dummy"));
                break;
        }
    }

    @Override
    public int getItemCount() {
        if(hetero_entity_list == null){
            return 0;
        }
        else {
            return hetero_entity_list.size();
        }
    }

     class user_profile_case_ViewHolder extends RecyclerView.ViewHolder{
        UserProfileBinding userProfileBinding;
        public user_profile_case_ViewHolder(@NonNull UserProfileBinding userProfileBinding) {
            super(userProfileBinding.getRoot());
            this.userProfileBinding = userProfileBinding;
            userProfileBinding.profileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupMenu(view);
                }
            });
        }
        void showPopupMenu(View v){
            PopupMenu popupMenu = new PopupMenu(v.getContext(),v);
            popupMenu.inflate(R.menu.profile_menu);
            popupMenu.show();
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    switch (menuItem.getItemId()){
                        case R.id.add_profile_pic :
                            profile_pic_interface.add_profile_pic_method();
                            break;
                        case R.id.logout_profile_pic:
                            profile_pic_interface.logout_profile_pic_method();
                            break;
                    }
                    return false;
                }
            });
        }
    }

    class user_property_case_ViewHolder extends RecyclerView.ViewHolder{
        PropertyLayoutBinding propertyLayoutBinding1;

        public user_property_case_ViewHolder(@NonNull PropertyLayoutBinding propertyLayoutBinding1) {
            super(propertyLayoutBinding1.getRoot());
            this.propertyLayoutBinding1 = propertyLayoutBinding1;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recycler_interafce_for_profile.onItemClick(getAdapterPosition());
                }
            });
        }
    }

}
