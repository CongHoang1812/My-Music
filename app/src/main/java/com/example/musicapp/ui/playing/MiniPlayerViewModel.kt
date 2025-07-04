package com.example.musicapp.ui.playing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import com.example.musicapp.data.model.song.Song
import com.example.musicapp.data.repository.song.SongRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MiniPlayerViewModel(
    private val songRepository: SongRepositoryImpl
) : ViewModel() {

    private val _mediaItems = MutableLiveData<List<MediaItem>>()
    private val _isPlaying = MutableLiveData<Boolean>()
    private val _currentPlayingSong = MutableLiveData<Song?>()
    val mediaItems: LiveData<List<MediaItem>> = _mediaItems
    val isPlaying : LiveData<Boolean> = _isPlaying
    val currentPlayingSong: LiveData<Song?> = _currentPlayingSong


    fun setMediaItem(mediaItems: List<MediaItem>){
        _mediaItems.value = mediaItems
    }
    fun setPlayingState(state: Boolean){
        _isPlaying.value = state
    }
    fun getCurrentPlayingSong(songId:String){
        viewModelScope.launch(Dispatchers.IO) {
            val result = songRepository.getSongById(songId)
            _currentPlayingSong.postValue(result)
        }
    }
    class Factory(
        private val songRepository: SongRepositoryImpl
    ): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MiniPlayerViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return MiniPlayerViewModel(songRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }

}