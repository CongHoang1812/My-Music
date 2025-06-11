package com.example.musicapp.data.source.remote

import com.example.musicapp.ResultCallback
import com.example.musicapp.data.model.song.SongList
import com.example.musicapp.data.source.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteSongDataSource :SongDataSource.Remote {
    override suspend fun loadSongs(callback: ResultCallback<Result<SongList>>) {
        withContext(Dispatchers.IO) {
            val response = RetrofitHelper.instance.loadSongs()
            if(response.isSuccessful){
                if(response.body() != null){
                    val songList = response.body()!!
                    callback.onResult(Result.Success(songList))
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