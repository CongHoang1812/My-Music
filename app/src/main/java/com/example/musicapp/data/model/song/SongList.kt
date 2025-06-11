package com.example.musicapp.data.model.song

import com.google.gson.annotations.SerializedName

data class SongList(
    @SerializedName("songs")
    val songs : List<Song> = emptyList()
)
