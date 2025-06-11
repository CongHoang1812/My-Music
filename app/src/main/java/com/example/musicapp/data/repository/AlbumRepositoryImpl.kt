package com.example.musicapp.data.repository

import com.example.musicapp.ResultCallback
import com.example.musicapp.data.model.album.Album
import com.example.musicapp.data.source.Result
import com.example.musicapp.data.source.remote.RemoteAlbumDataSource

class AlbumRepositoryImpl : AlbumRepository.Local, AlbumRepository.Remote {
    private val remoteAlbumDataSource =  RemoteAlbumDataSource()
    override suspend fun loadAlbums(callback: ResultCallback<Result<List<Album>>>) {
        remoteAlbumDataSource.loadAlbums(callback);
    }

}