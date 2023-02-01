package com.example.project1.RecyclerAdapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project1.ListenerInterfaces.recyclerInterface;
import com.example.project1.Property_model_class;
import com.example.project1.databinding.PropertyLayoutBinding;

import java.util.List;

public class property_adapter extends RecyclerView.Adapter<property_adapter.ViewHolder> {

    private List<Property_model_class> datalist;
    private Context context;
    private recyclerInterface recycler_Interface;

    public property_adapter(List<Property_model_class>datalist, Context context, recyclerInterface recycler_Interface) {
        this.datalist = datalist;
        this.context = context;
        this.recycler_Interface = recycler_Interface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        PropertyLayoutBinding propertyLayoutBinding = PropertyLayoutBinding.inflate(layoutInflater,parent,false);
        return new ViewHolder(propertyLayoutBinding);
//        parent.getContext()
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

//        int imagedata = datalist.get(position).getProperty_image();
        String adressdata = datalist.get(position).getAdress();
        String phnodata = datalist.get(position).getPhone_number();
        Log.d("phone no print kiya hain, dekh adress siiciiv mil rha hain kya",phnodata);
        String pricedata = datalist.get(position).getPrice();
        String detailsdata = datalist.get(position).getDetails();
        String offerdbydata = datalist.get(position).getOfferedby();
        String imagedata = datalist.get(position).getProperty_image();
        holder.propertyLayoutBinding.setPropertyModelClassData(new Property_model_class(phnodata,adressdata,pricedata,detailsdata,offerdbydata,imagedata,"dummy","dummy","dummy","dummy"));
        holder.propertyLayoutBinding.executePendingBindings();
    }




    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        PropertyLayoutBinding propertyLayoutBinding;

        public ViewHolder(@NonNull PropertyLayoutBinding propertyLayoutBinding) {
            super(propertyLayoutBinding.getRoot());
            this.propertyLayoutBinding = propertyLayoutBinding;
            propertyLayoutBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recycler_Interface.onItemClick(getAdapterPosition());
//                    Toast.makeText(context, String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
