package com.example.musicapp.data.model.song

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity(tableName = "songs" )
open class Song(
    @SerializedName("id") @PrimaryKey @ColumnInfo(name = "song_id") var id: String = "",
    @SerializedName("title") var title: String = "",
    @SerializedName("album") var album: String = "",
    @SerializedName("artist") var artist: String = "",
    @SerializedName("source") var source: String = "",
    @SerializedName("image") var image: String = "",
    @SerializedName("duration") var duration: Int = 0,
    @SerializedName("favorite") var favorite: Boolean = false,
    @SerializedName("counter") var counter: Int = 0,
    @SerializedName("replay") var replay: Int = 0
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Song) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
