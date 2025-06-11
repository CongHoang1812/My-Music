package com.example.musicapp.data.repository.song

import com.example.musicapp.ResultCallback
import com.example.musicapp.data.model.song.Song
import com.example.musicapp.data.model.song.SongList
import com.example.musicapp.data.source.Result
import com.example.musicapp.data.source.SongDataSource
import com.example.musicapp.data.source.remote.RemoteSongDataSource
import kotlinx.coroutines.flow.Flow

class SongRepositoryImpl(
    private val localSongDataSource: SongDataSource.Local
) : SongRepository.Local, SongRepository.Remote {
    private val remoteSongDataSource =  RemoteSongDataSource()


    override suspend fun loadSongs(callback: ResultCallback<Result<SongList>>) {
        remoteSongDataSource.loadSongs(callback)
    }

    override val songs: List<Song>
        get() = localSongDataSource.songs

    override val favoriteSongs: Flow<List<Song>>
        get() = localSongDataSource.favoriteSongs

    override suspend fun getSongById(id: String): Song? {
        return localSongDataSource.getSongById(id)
    }

    override suspend fun insert(vararg songs: Song) {
        localSongDataSource.insert(*songs)
    }

    override suspend fun delete(song: Song) {
        localSongDataSource.delete(song)
    }

    override suspend fun update(song: Song) {
        localSongDataSource.update(song)
    }

    override suspend fun updateFavorite(id: String, favorite: Boolean) {
        localSongDataSource.updateFavorite(id, favorite)
    }


}