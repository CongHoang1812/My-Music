package com.example.musicapp.ui.home.recommended.more

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicapp.data.model.song.Song

class MoreRecommendedViewModel : ViewModel() {
    private val  _recommendedSong  = MutableLiveData<List<Song>>()
    val recommendedSong : MutableLiveData<List<Song>>
        get() = _recommendedSong

    fun setRecommendedSong(recommendedSong: List<Song>){
        _recommendedSong.value = recommendedSong
    }

}