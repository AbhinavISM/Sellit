package com.example.project1.data_classes;

import android.os.Parcel;
import android.os.Parcelable;

public class Property_model_class implements Parcelable {

    private String Phone_number;
    private String Adress;
    private String Price;
    private String Details;
    private String Offeredby;

    public String getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

    public String getLng() {
        return Lng;
    }

    public void setLng(String lng) {
        Lng = lng;
    }

    private String Property_image;
    private String property_ID;
    private String property_ID_particular;
    private String Lat;
    private String Lng;

    public Property_model_class(){

    }

    public Property_model_class(String phone_number, String adress, String price, String details, String offeredby, String property_image , String property_ID, String property_ID_particular, String lat, String lng) {
        Phone_number = phone_number;
        Adress = adress;
        Price = price;
        Details = details;
        Offeredby = offeredby;
        Property_image = property_image;
        this.property_ID = property_ID;
        this.property_ID_particular = property_ID_particular;
        Lat = lat;
        Lng = lng;
    }

    protected Property_model_class(Parcel in) {
        Phone_number = in.readString();
        Adress = in.readString();
        Price = in.readString();
        Details = in.readString();
        Offeredby = in.readString();
        Property_image = in.readString();
        property_ID = in.readString();
        property_ID_particular = in.readString();
        Lat = in.readString();
        Lng = in.readString();
    }

    public static final Creator<Property_model_class> CREATOR = new Creator<Property_model_class>() {
        @Override
        public Property_model_class createFromParcel(Parcel in) {
            return new Property_model_class(in);
        }

        @Override
        public Property_model_class[] newArray(int size) {
            return new Property_model_class[size];
        }
    };

    public String getProperty_ID() {
        return property_ID;
    }

    public void setProperty_ID(String property_ID) {
        this.property_ID = property_ID;
    }

    public String getProperty_ID_particular() {
        return property_ID_particular;
    }

    public void setProperty_ID_particular(String property_ID_particular) {
        this.property_ID_particular = property_ID_particular;
    }

    public String getPhone_number() {
        return Phone_number;
    }

    public void setPhone_number(String phone_number) {
        Phone_number = phone_number;
    }

    public String getAdress() {
        return Adress;
    }

    public void setAdress(String adress) {
        Adress = adress;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }

    public String getOfferedby() {
        return Offeredby;
    }

    public void setOfferedby(String offeredby) {
        Offeredby = offeredby;
    }

    public String getProperty_image() {
        return Property_image;
    }

    public void setProperty_image(String property_image) {
        Property_image = property_image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Phone_number);
        parcel.writeString(Adress);
        parcel.writeString(Price);
        parcel.writeString(Details);
        parcel.writeString(Offeredby);
        parcel.writeString(Property_image);
        parcel.writeString(property_ID);
        parcel.writeString(property_ID_particular);
        parcel.writeString(Lat);
        parcel.writeString(Lng);
    }
}
