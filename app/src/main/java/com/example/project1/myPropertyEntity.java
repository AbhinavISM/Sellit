package com.example.project1;

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
    private String Property_image;

    public myPropertyEntity(String Phone_number, String Adress, String Price, String Details, String Offeredby, String Property_image) {
        this.Phone_number = Phone_number;
        this.Adress = Adress;
        this.Price = Price;
        this.Details = Details;
        this.Offeredby = Offeredby;
        this.Property_image = Property_image;
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
