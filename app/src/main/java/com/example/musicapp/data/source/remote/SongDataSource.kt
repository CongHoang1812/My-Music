package com.example.musicapp.data.source.remote

import com.example.musicapp.ResultCallback
import com.example.musicapp.data.model.song.SongList

interface SongDataSource {
    interface Local{
        // todo
    }

    interface Remote{
        suspend fun loadSongs(callback: ResultCallback<com.example.musicapp.data.source.Result<SongList>>)
    }
}