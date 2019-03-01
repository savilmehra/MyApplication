package com.realtimelocation.myapplication;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface DAOForRoom {

    @Insert
    void insert(TableEntity... tableEntities);

    @Query("SELECT DISTINCT Artist FROM music_data")
    List<String> getAllArtists();

    @Query("SELECT DISTINCT Album FROM music_data")
    List<String> getAllAlbumb();

    @Query("SELECT Name FROM music_data WHERE Artist=:s ")
    List<String> getAllSsongsOfArtist(String s);

    @Query("SELECT Name FROM music_data WHERE Album=:s ")
    List<String> getAllSsongsOfAlbumb(String s);

    @Query("DELETE FROM music_data")
     void nukeTable();
}
