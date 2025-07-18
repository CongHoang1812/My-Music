package com.example.musicapp.data.repository.artist

import com.example.musicapp.ResultCallback
import com.example.musicapp.data.model.artist.Artist
import com.example.musicapp.data.model.artist.ArtistList
import com.example.musicapp.data.model.artist.ArtistSongCrossRef
import com.example.musicapp.data.model.artist.ArtistWithSongs
import com.example.musicapp.data.source.Result
import kotlinx.coroutines.flow.Flow

interface ArtistRepository {
    interface Local {
        val artists: Flow<List<Artist>>

        val top15Artists: Flow<List<Artist>>

        fun getArtistWithSongs(artistId: Int): ArtistWithSongs

        fun getArtistById(id: Int): Flow<Artist?>

        suspend fun insert(vararg artists: Artist)

        suspend fun insertArtistSongCrossRef(vararg artistSongCrossRef: ArtistSongCrossRef)

        suspend fun update(artist: Artist)

        suspend fun delete(artist: Artist)
    }

        interface Remote {
            suspend fun loadArtists(callback: ResultCallback<Result<ArtistList>>)
        }


}