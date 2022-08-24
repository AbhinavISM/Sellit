package com.example.project1;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class property_adapter extends RecyclerView.Adapter<property_adapter.ViewHolder> {

    private List<property_model_class> datalist;
    private Context context;
    private recyclerInterface recycler_Interface;

    public property_adapter(List<property_model_class>datalist,Context context,recyclerInterface recycler_Interface) {
        this.datalist = datalist;
        this.context = context;
        this.recycler_Interface = recycler_Interface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.property_layout,parent,false);
        return new ViewHolder(view);
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
        holder.setdata(adressdata, phnodata, pricedata, detailsdata, offerdbydata,imagedata);

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView property_image;
        TextView phone_number;
        TextView adress;
        TextView price;
        TextView details;
        TextView offeredby;

        public ViewHolder(@NonNull View itemView) {
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
                    recycler_Interface.onItemClick(getAdapterPosition());
//                    Toast.makeText(context, String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
                }
            });

        }
        public void setdata( String adressdata, String phnodata, String pricedata, String detailsdata, String offerdbydata, String imagedata){
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
