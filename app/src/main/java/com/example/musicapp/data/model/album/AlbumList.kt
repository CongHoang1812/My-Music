package com.example.musicapp.data.model.album

import com.google.gson.annotations.SerializedName

data class AlbumList(
    @SerializedName("playlists")
    val album : List<Album> = emptyList()
)
