package com.example.project1.RoomDb;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "myPropertyRoomTable")
public class myPropertyEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String Phone_number;
    private String Adress;
    private String Price;
    private String Details;
    private String Offeredby;

    public String getLat() {
        return Lat;
    }

    public String getLng() {
        return Lng;
    }

    private String Property_image;
    private String Lat;
    private String Lng;

    private int viewtype_room;
    private String Profile_username_room;
    private String Profile_useremail_room;
    private String Profile_image_room;

    public int getViewtype_room() {
        return viewtype_room;
    }

    public String getProfile_username_room() {
        return Profile_username_room;
    }

    public String getProfile_useremail_room() {
        return Profile_useremail_room;
    }

    public String getProfile_image_room() {
        return Profile_image_room;
    }

    public myPropertyEntity(String Phone_number, String Adress, String Price, String Details, String Offeredby, String Property_image, int viewtype_room, String Profile_username_room , String Profile_useremail_room , String Profile_image_room, String Lat, String Lng) {
        this.Phone_number = Phone_number;
        this.Adress = Adress;
        this.Price = Price;
        this.Details = Details;
        this.Offeredby = Offeredby;
        this.Property_image = Property_image;
        this.Lat = Lat;
        this.Lng = Lng;

        this.viewtype_room = viewtype_room;
        this.Profile_username_room = Profile_username_room;
        this.Profile_useremail_room = Profile_useremail_room;
        this.Profile_image_room = Profile_image_room;
    }

    public int getId() {
        return id;
    }

    public String getPhone_number() {
        return Phone_number;
    }

    public String getAdress() {
        return Adress;
    }

    public String getPrice() {
        return Price;
    }

    public String getDetails() {
        return Details;
    }

    public String getOfferedby() {
        return Offeredby;
    }

    public String getProperty_image() {
        return Property_image;
    }

    public void setId(int id) {
        this.id = id;
    }
}
