package com.example.project1.RoomDb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface myPropertydao {
    @Insert
    void insert_property_offline(myPropertyEntity my_property_entity);

    @Update
    void update_property_offline(myPropertyEntity my_property_entity);

    @Delete
    void delete_property_offline(myPropertyEntity my_property_entity);

    @Query("SELECT * FROM myPropertyRoomTable")
    LiveData<List<myPropertyEntity>> getAllmyProperty();
}
