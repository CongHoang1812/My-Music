package com.example.musicapp.data.source.local.artist

import com.example.musicapp.data.model.artist.Artist
import com.example.musicapp.data.model.artist.ArtistSongCrossRef
import com.example.musicapp.data.model.artist.ArtistWithSongs
import com.example.musicapp.data.source.remote.ArtistDataSource
import kotlinx.coroutines.flow.Flow

class LocalArtistDataSource(
    private val artistDao: ArtistDao
) : ArtistDataSource.Local {
    override val artists: Flow<List<Artist>>
        get() = artistDao.artists

    override val top15Artists: Flow<List<Artist>>
        get() = artistDao.top15Artists

    override fun getArtistWithSongs(artistId: Int): ArtistWithSongs {
        return artistDao.getArtistWithSongs(artistId)
    }

    override fun getArtistById(id: Int): Flow<Artist?> {
        return artistDao.getArtistById(id)
    }

    override suspend fun insert(vararg artists: Artist) {
        artistDao.insert(*artists)
    }

    override suspend fun delete(artist: Artist) {
        artistDao.delete(artist)
    }

    override suspend fun update(artist: Artist) {
        artistDao.update(artist)
    }
    override suspend fun insertArtistSongCrossRef(vararg artistSongCrossRef: ArtistSongCrossRef) {
        artistDao.insertArtistSongCrossRef(*artistSongCrossRef)
    }
}