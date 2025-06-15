package com.example.musicapp.data.source.remote

import com.example.musicapp.ResultCallback
import com.example.musicapp.data.model.artist.Artist
import com.example.musicapp.data.model.artist.ArtistList
import com.example.musicapp.data.model.song.SongList
import com.example.musicapp.data.source.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteArtistDataSource :ArtistDataSource.Remote {
    override suspend fun loadArtists(callback: ResultCallback<Result<ArtistList>>) {
        withContext(Dispatchers.IO) {
            val response = RetrofitHelper.instance.loadArtists()
            if(response.isSuccessful){
                if(response.body() != null){
                    val artistList = response.body()!!
                    callback.onResult(Result.Success(artistList))
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