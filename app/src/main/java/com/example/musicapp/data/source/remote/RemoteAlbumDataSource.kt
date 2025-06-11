package com.example.musicapp.data.source.remote

import com.example.musicapp.ResultCallback
import com.example.musicapp.data.model.album.Album
import com.example.musicapp.data.source.AlbumDataSource
import com.example.musicapp.data.source.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteAlbumDataSource : AlbumDataSource.Remote {
    override suspend fun loadAlbums(callback: ResultCallback<Result<List<Album>>>) {
        withContext(Dispatchers.IO) {
            val response = RetrofitHelper.instance.loadAlbums()
            if(response.isSuccessful){
                if(response.body() != null){
                    val albums = response.body()!!.album
                    callback.onResult(Result.Success(albums))
                }else{
                    callback.onResult(Result.Error(Exception("Response body is null")))
                }
            }
            else{
                callback.onResult(Result.Error(Exception("Response is not successful")))
            }

        }
    }


}