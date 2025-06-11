package com.example.musicapp.data.repository

import com.example.musicapp.ResultCallback
import com.example.musicapp.data.model.album.Album
import com.example.musicapp.data.source.Result

interface AlbumRepository {
    interface Local{
    //todo
    }
    interface Remote{
        suspend fun loadAlbums(callback: ResultCallback<Result<List<Album>>>)
    }
}