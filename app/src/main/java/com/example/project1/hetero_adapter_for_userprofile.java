package com.example.project1;

import static com.example.project1.hetero_model_for_userprofile.user_profile_case;
import static com.example.project1.hetero_model_for_userprofile.user_property_case;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class hetero_adapter_for_userprofile extends RecyclerView.Adapter{

    List<hetero_model_for_userprofile> hetero_list;
    Context context;
    recyclerInterface recycler_interafce_for_profile;
    add_profile_pic_interface profile_pic_interface;

    @Override
    public int getItemViewType(int position) {
        return hetero_list.get(position).getViewtype();
    }

    public hetero_adapter_for_userprofile(List<hetero_model_for_userprofile> hetero_list, Context context,recyclerInterface reycler_interafce_for_profile,add_profile_pic_interface profile_pic_interface) {
        this.hetero_list = hetero_list;
        this.context = context;
        this.recycler_interafce_for_profile = reycler_interafce_for_profile;
        this.profile_pic_interface = profile_pic_interface;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch(viewType){
            case user_profile_case:
                View profileview= LayoutInflater.from(context).inflate(R.layout.user_profile,parent,false);
                return new user_profile_case_ViewHolder(profileview);
            case user_property_case:
                View propertyview= LayoutInflater.from(context).inflate(R.layout.property_layout,parent,false);
                return new user_property_case_ViewHolder(propertyview);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (hetero_list.get(position).getViewtype()){
            case user_profile_case:
                String profile_image_data = hetero_list.get(position).getProfile_image();
                String profile_username_fromfirebase_data = hetero_list.get(position).getProfile_username_fromfirebase();
                String profile_useremail_fromfirebase_data = hetero_list.get(position).getProfile_useremail_fromirebase();
                ((user_profile_case_ViewHolder)holder).setdata1(profile_image_data,profile_username_fromfirebase_data,profile_useremail_fromfirebase_data);
                break;
            case user_property_case:
                String adressdata = hetero_list.get(position).getAdress();
                String phnodata = hetero_list.get(position).getPhone_number();
                Log.d("phone no print kiya hain, dekh adress siiciiv mil rha hain kya",phnodata);
                String pricedata = hetero_list.get(position).getPrice();
                String detailsdata = hetero_list.get(position).getDetails();
                String offerdbydata = hetero_list.get(position).getOfferedby();
                String imagedata = hetero_list.get(position).getProperty_image();
                ((user_property_case_ViewHolder)holder).setdata2(adressdata, phnodata, pricedata, detailsdata, offerdbydata,imagedata);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return hetero_list.size();
    }

     class user_profile_case_ViewHolder extends RecyclerView.ViewHolder{
        ImageView profile_image;
        TextView profile_username_fromfirebase;
        TextView profile_useremail_fromfirebase;
        public user_profile_case_ViewHolder(@NonNull View itemView) {
            super(itemView);
            profile_image = itemView.findViewById(R.id.profile_image);
            profile_username_fromfirebase = itemView.findViewById(R.id.profile_username_fromfirebase);
            profile_useremail_fromfirebase = itemView.findViewById(R.id.profile_useremail_fromfirebase);
            profile_image.setOnClickListener(new View.OnClickListener() {
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

        public void setdata1(String profile_image_data, String profile_username_fromfirebase_data, String profile_useremail_fromfirebase_data){
            profile_username_fromfirebase.setText(profile_username_fromfirebase_data);
            profile_useremail_fromfirebase.setText(profile_useremail_fromfirebase_data);
            Picasso.get().load(profile_image_data).into(profile_image);
        }
    }

    class user_property_case_ViewHolder extends RecyclerView.ViewHolder{
        ImageView property_image;
        TextView phone_number;
        TextView adress;
        TextView price;
        TextView details;
        TextView offeredby;

        public user_property_case_ViewHolder(@NonNull View itemView) {
            super(itemView);
            property_image = itemView.findViewById(R.id.property_image);
            adress = itemView.findViewById(R.id.adress);
            phone_number = itemView.findViewById(R.id.phone_number);
            price = itemView.findViewById(R.id.price);
            details = itemView.findViewById(R.id.details);
            offeredby = itemView.findViewById(R.id.offered_by);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recycler_interafce_for_profile.onItemClick(getAdapterPosition());
                }
            });
        }

        private void setdata2( String adressdata, String phnodata, String pricedata, String detailsdata, String offerdbydata, String imagedata){
            adress.setText(adressdata);
            phone_number.setText(phnodata);
            price.setText(pricedata);
            details.setText(detailsdata);
            offeredby.setText(offerdbydata);
            Log.d("image url",imagedata);
            Picasso.get().load(imagedata).into(property_image);
//            property_image.setImageResource(imagedata);
//            Glide.with(context).load(imagedata).into(property_image);
        }
    }

}
