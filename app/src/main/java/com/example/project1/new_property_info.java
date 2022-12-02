package com.example.project1;

public class new_property_info {

//    private String name;
//    private String adress;
//    private String phoneno;
//    private String details;
//    private String price;
//    private String image_uri;

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



//    public new_property_info(String phoneno, String adress, String price, String details, String name, String image_uri){
//        this.name = name;
//        this.adress = adress;
//        this.phoneno = phoneno;
//        this.details = details;
//        this.price = price;
//        this.image_uri = image_uri;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getAdress() {
//        return adress;
//    }
//
//    public void setAdress(String adress) {
//        this.adress = adress;
//    }
//
//    public String getPhoneno() {
//        return phoneno;
//    }
//
//    public void setPhoneno(String phoneno) {
//        this.phoneno = phoneno;
//    }
//
//    public String getDetails() {
//        return details;
//    }
//
//    public void setDetails(String details) {
//        this.details = details;
//    }
//
//    public String getPrice() {
//        return price;
//    }
//
//    public void setPrice(String price) {
//        this.price = price;
//    }
//
//    public String getImage_uri() {
//        return image_uri;
//    }
//
//    public void setImage_uri(String image_uri) {
//        this.image_uri = image_uri;
//    }

}
