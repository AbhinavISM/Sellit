package com.example.project1;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = myPropertyEntity.class, version = 5)
public abstract class myPropertydb extends RoomDatabase {

    public static myPropertydb myPropertydbinstance;
    public abstract myPropertydao my_property_dao();

    public static synchronized myPropertydb getInstance(Context context){
        if(myPropertydbinstance==null){
            myPropertydbinstance = Room.databaseBuilder(context.getApplicationContext(),myPropertydb.class,"my_property_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return myPropertydbinstance;
    }
}
