package com.example.project1.data_classes;

public class new_property_info {
    private String Phone_number;
    private String Adress;
    private String Price;
    private String Details;
    private String Offeredby;
    private String Property_image;
    private String property_ID;
    private String property_ID_particular;
    private String Lat;

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

    private String Lng;

    public new_property_info(){

    }

    public new_property_info(String phone_number, String adress, String price, String details, String offeredby, String property_image, String property_ID, String property_ID_particular, String lat, String lng) {
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

}
