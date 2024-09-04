package com.example.salesapp.database.remote.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.salesapp.database.remote.model.SalesEntities;

@Database(entities = {SalesEntities.class}, version = 2)
public abstract class SalesDatabase extends RoomDatabase {

    public static final String AppDatabase_Name = "SalesDB";

    public abstract SaleDao saleDao();

    private static volatile SalesDatabase INSTANCE;

    public static SalesDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SalesDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    SalesDatabase.class, AppDatabase_Name)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
