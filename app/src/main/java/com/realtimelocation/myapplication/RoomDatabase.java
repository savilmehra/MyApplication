package com.realtimelocation.myapplication;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.content.Context;


@Database(entities = {TableEntity.class,}, version = 1, exportSchema = false)

public abstract class RoomDatabase extends android.arch.persistence.room.RoomDatabase {

    private static RoomDatabase db;

    public abstract DAOForRoom daoForRoom();

    public static RoomDatabase getAppDatabase(Context context) {

        if (db == null) {
            synchronized (RoomDatabase.class) {
                if (db == null) {

                    db = Room.
                            databaseBuilder(context.getApplicationContext(), RoomDatabase.class, "RoomDb")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();

                }

            }
        }
        return db;
    }


}
