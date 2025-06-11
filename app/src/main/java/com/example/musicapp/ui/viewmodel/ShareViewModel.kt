package com.example.musicapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.musicapp.data.model.PlayingSong
import com.example.musicapp.data.model.RecentSong
import com.example.musicapp.data.model.playlist.Playlist
import com.example.musicapp.data.model.song.Song
import com.example.musicapp.data.repository.recent.RecentSongRepositoryImpl
import com.example.musicapp.data.repository.song.SongRepositoryImpl
import com.example.musicapp.utils.MusicAppUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ShareViewModel private constructor(
    private val songRepository: SongRepositoryImpl,
    private val recentSongRepository: RecentSongRepositoryImpl

): ViewModel() {

    private val _playingSong = PlayingSong()
    private val _playingSongLiveData = MutableLiveData<PlayingSong>()
    private val _currentPlaylist = MutableLiveData<Playlist>()
    private val _playLists : MutableMap<String, Playlist> = HashMap()
    private val _indexToPlay: MutableLiveData<Int> = MutableLiveData()
    private val _isReady = MutableLiveData<Boolean>()
    val playingSong: LiveData<PlayingSong>
        get() = _playingSongLiveData
    val currentPlaylist: LiveData<Playlist>
        get() = _currentPlaylist
    val indexToPlay: LiveData<Int>
        get() = _indexToPlay
    val isReady: LiveData<Boolean> = _isReady

    init {
        if (_instance == null) {
            synchronized(ShareViewModel::class.java) {
                if (_instance == null) {
                    _instance = this
                }
            }
        }
    }

     fun initPlayList() {
        for(playListName: MusicAppUtils.DefaultPlaylistName in MusicAppUtils.DefaultPlaylistName.entries.toTypedArray()){
            val playlist = Playlist(_id = -1, name = playListName.value)
            _playLists[playListName.value] = playlist
        }
    }
    fun updateFavoriteStatus(song: Song){
        viewModelScope.launch(Dispatchers.IO) {
            songRepository.updateFavorite(song.id, song.favorite)
        }
    }


    fun insertRecentSongToDB(song: Song){
        val recentSong = createRecentSong(song)
        viewModelScope.launch(Dispatchers.IO) {
            recentSongRepository.insert(recentSong)

        }
    }
    private fun createRecentSong(song:Song): RecentSong{
        return RecentSong.Builder(song).build()
    }
    fun setUpPlayList(songs: List<Song>, playlistName: String){
        val playlist = _playLists.getOrDefault(playlistName, null)
        playlist?.let {
            it.updateSongList(songs)
            updatePlayList(it)
            _isReady.value = true
        }
    }
    fun setPlayingSong(index: Int){
        val currentPlaylistSize = currentPlaylist.value?.songs?.size
        if(index >= 0 && currentPlaylistSize  != null &&  currentPlaylistSize > index){
            val song = currentPlaylist.value?.songs?.get(index)!!
            _playingSong.song = song
            _playingSong.currentIndex = index
            _playingSong.playlist = currentPlaylist.value
            updatePlayingSong()

        }
    }

    private fun updatePlayingSong() {
        _playingSongLiveData.value = _playingSong
    }

    private fun updatePlayList(playlist : Playlist){
        _playLists[playlist.name] = playlist
    }
    fun setCurrentPlaylist(playlistName: String){
        val playlist = _playLists.getOrDefault(playlistName, null)
        playlist?.let {
            _currentPlaylist.value = it
        }
    }
    fun setIndexToPlay(index: Int){
        _indexToPlay.value = index
    }

    fun updateSongInDB(song: Song){
        viewModelScope.launch(Dispatchers.IO) {
            ++song.counter
            ++song.replay
            songRepository.update(song)
        }
    }

    fun addPlaylist(playlist : Playlist){
        updatePlayList(playlist)
    }

    fun loadPreviousSessionSong(songId: String?, playlistName: String?){
        if(playlistName!= null){
            setCurrentPlaylist(playlistName)
        }
        var playlist = _playLists.getOrDefault(playlistName, null)
        if(playlist == null){
            playlist = _playLists.getOrDefault(MusicAppUtils.DefaultPlaylistName.DEFAULT.value, null)
            if(songId != null && playlist != null){
                _playingSong.playlist = playlist
                val songList = playlist.songs
                val index = songList.indexOfFirst {it.id ==songId  }
                if(index >= 0){
                    val song = songList[index]
                    _playingSong.song = song
                    _playingSong.currentIndex = index
                    setIndexToPlay(index)

                }
                updatePlayingSong()
            }
        }
    }

    class Factory(
        private val recentSongRepository: RecentSongRepositoryImpl,
        private val songRepository: SongRepositoryImpl
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ShareViewModel::class.java)) {
                return ShareViewModel(songRepository,recentSongRepository) as T
            } else {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

    companion object {
        private var _instance: ShareViewModel? = null
        val instance: ShareViewModel
            get() = _instance!!
    }

}