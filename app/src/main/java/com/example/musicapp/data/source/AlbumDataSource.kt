package com.example.musicapp.data.source

import com.example.musicapp.ResultCallback
import com.example.musicapp.data.model.album.Album

interface AlbumDataSource {
    interface Local{
        // todo
    }

    interface Remote{
        suspend fun loadAlbums(callback: ResultCallback<com.example.musicapp.data.source.Result<List<Album>>>)
    }
}