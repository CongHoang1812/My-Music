package com.example.musicapp

import android.app.Application
import android.content.ComponentName
import androidx.media3.session.MediaController

import androidx.media3.session.SessionToken
import com.example.musicapp.data.repository.playlist.PlaylistRepositoryImpl
import com.example.musicapp.data.repository.recent.RecentSongRepositoryImpl
import com.example.musicapp.data.repository.song.SongRepositoryImpl
import com.example.musicapp.ui.playing.PlayBackService
import com.example.musicapp.ui.viewmodel.MediaPlayerViewModel
import com.example.musicapp.utils.InjectionUtils
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import java.util.concurrent.ExecutionException

class MusicApplication: Application() {
    private lateinit var controllerFuture: ListenableFuture<MediaController>
    private var mediaController: MediaController? = null
    private lateinit var recentSongRepository: RecentSongRepositoryImpl
    private lateinit var songRepository: SongRepositoryImpl
    private lateinit var playlistRepository: PlaylistRepositoryImpl

    override fun onCreate() {
        super.onCreate()
        createMediaPlayer()
        setUpComponents()
    }

    private fun setUpComponents() {
         val songDataSource = InjectionUtils.provideSongDataSource(applicationContext)
        songRepository = InjectionUtils.provideSongRepository(songDataSource)
        val recentSongDataSource = InjectionUtils.provideRecentSongDataSource(applicationContext)
        recentSongRepository = InjectionUtils.provideRecentSongRepository(recentSongDataSource)
        val playlistDataSource = InjectionUtils.providePlaylistDataSource(applicationContext)
        playlistRepository = InjectionUtils.providePlaylistRepository(playlistDataSource)
    }
    fun getRecentSongRepository() : RecentSongRepositoryImpl{
        return recentSongRepository
    }

    private fun createMediaPlayer() {
        val sessionToken = SessionToken(applicationContext, ComponentName(applicationContext, PlayBackService::class.java))
        controllerFuture = MediaController.Builder(applicationContext, sessionToken).buildAsync()
        controllerFuture.addListener({
            if(controllerFuture.isDone && !controllerFuture.isCancelled){
                try{
                    mediaController = controllerFuture.get()
                    mediaController?.let{
                        MediaPlayerViewModel.instance.setMediaController(it)
                    }
                } catch (ignored: ExecutionException){

                }catch (ignored: InterruptedException){

                }
            }else{
                mediaController = null
            }
        }, MoreExecutors.directExecutor())

    }
    fun getSongRepository(): SongRepositoryImpl{
        return songRepository
    }
    fun getPlaylistRepository(): PlaylistRepositoryImpl{
        return playlistRepository
    }
}