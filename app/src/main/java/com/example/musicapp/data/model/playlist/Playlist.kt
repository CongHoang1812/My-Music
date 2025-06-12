package com.example.musicapp.data.model.playlist

import androidx.media3.common.MediaItem
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.musicapp.data.model.song.Song
import java.util.Date
@Entity(tableName = "playlists")
data class Playlist(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "playlist_id")
    private var _id: Int = 10001,

    var name: String = "",
    var artwork: String?=null,
     @ColumnInfo(name = "create_at")
    var createAt: Date? = null

){
     @Ignore
    private val _mediaItem : MutableList<MediaItem> = mutableListOf()
    @Ignore
    var songs: List<Song> = listOf()
    var id: Int
        get() = _id
        set(value){
            _id = if(value>0){
                id
            }else{
                autoId++
            }
        }
    val mediaItem: List<MediaItem>
        get() = _mediaItem

    companion object{
        private var autoId = 10001
    }
    fun updateSongList(songs: List<Song>){
        this.songs = songs
        updateMediaItems()
    }



    private fun updateMediaItems() {
        _mediaItem.clear()
        songs.forEach{ song ->
            _mediaItem.add(MediaItem.fromUri(song.source))

        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Playlist

        return _id == other._id
    }

    override fun hashCode(): Int {
        return _id
    }
}
