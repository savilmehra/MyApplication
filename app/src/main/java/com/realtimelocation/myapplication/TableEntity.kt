package com.realtimelocation.myapplication

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "music_data")
class TableEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    @SerializedName("Name")
    @Expose
    @ColumnInfo(name = "Name")
    var name: String? = null
    @SerializedName("Artist")
    @Expose
    @ColumnInfo(name = "Artist")
    var artist: String? = null
    @SerializedName("Album")
    @Expose
    @ColumnInfo(name = "Album")
    var album: String? = null

}
