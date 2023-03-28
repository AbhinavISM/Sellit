package com.example.project1.data_classes;

public class Hetero_model_for_userprofile {
    public static final int user_profile_case = 1;
    public static final int user_property_case = 2;

    private int Viewtype;

    public int getViewtype() {
        return Viewtype;
    }

    public void setViewtype(int viewtype) {
        Viewtype = viewtype;
    }

    public Hetero_model_for_userprofile(){

    }

    //shown for user property case
    private String Phone_number;
    private String Adress;
    private String Price;
    private String Details;
    private String Offeredby;
    private String Property_image;
    private String property_ID;
    private String property_ID_paticular;
    private String Lat;
    private String Lng;

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

    public Hetero_model_for_userprofile(String phone_number, String adress, String price, String details, String offeredby, String property_image, String property_ID, String property_ID_paticular, String lat,String lng) {
        Phone_number = phone_number;
        Adress = adress;
        Price = price;
        Details = details;
        Offeredby = offeredby;
        Property_image = property_image;
        this.property_ID = property_ID;
        this.property_ID_paticular = property_ID_paticular;
        Lat = lat;
        Lng = lng;
        Viewtype = 2;
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

    public String getProperty_ID() {
        return property_ID;
    }

    public void setProperty_ID(String property_ID) {
        this.property_ID = property_ID;
    }

    public String getProperty_ID_paticular() {
        return property_ID_paticular;
    }

    public void setProperty_ID_paticular(String property_ID_paticular) {
        this.property_ID_paticular = property_ID_paticular;
    }

    //shown for user profile case
    private String Profile_username_fromfirebase;
    private String Profile_useremail_fromirebase;
    private String Profile_image;

    public Hetero_model_for_userprofile(int viewtype, String profile_username_fromfirebase, String profile_useremail_fromirebase, String profile_image){
        Profile_username_fromfirebase = profile_username_fromfirebase;
        Profile_useremail_fromirebase = profile_useremail_fromirebase;
        Profile_image= profile_image;
        Viewtype = viewtype;
    }

    public String getProfile_username_fromfirebase() {
        return Profile_username_fromfirebase;
    }

    public void setProfile_username_fromfirebase(String profile_username_fromfirebase) {
        Profile_username_fromfirebase = profile_username_fromfirebase;
    }

    public String getProfile_useremail_fromirebase() {
        return Profile_useremail_fromirebase;
    }

    public void setProfile_useremail_fromirebase(String profile_useremail_fromirebase) {
        Profile_useremail_fromirebase = profile_useremail_fromirebase;
    }

    public String getProfile_image() {
        return Profile_image;
    }

    public void setProfile_image(String profile_image) {
        Profile_image = profile_image;
    }

}
