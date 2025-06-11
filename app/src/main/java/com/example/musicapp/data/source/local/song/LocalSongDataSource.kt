package com.example.musicapp.data.source.local.song

import com.example.musicapp.data.model.song.Song
import com.example.musicapp.data.source.SongDataSource
import kotlinx.coroutines.flow.Flow

class LocalSongDataSource(
    private val songDao: SongDao
) :SongDataSource.Local{
    override val songs: List<Song>
        get() = songDao.songs


    override val favoriteSongs: Flow<List<Song>>
        get() = songDao.favoriteSongs

    override suspend fun getSongById(id: String): Song? {
        return songDao.getSongById(id)
    }

    override suspend fun insert(vararg songs: Song) {
        songDao.insert(*songs)
    }

    override suspend fun delete(song: Song) {
        songDao.delete(song)
    }

    override suspend fun update(song: Song) {
        songDao.update(song)
    }

    override suspend fun updateFavorite(id: String, favorite: Boolean) {
        songDao.updateFavorite(id, favorite)
    }

}