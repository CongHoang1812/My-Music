package com.example.musicapp.ui.dialog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicapp.data.model.song.Song
import com.example.musicapp.utils.OptionMenuUtils

class SongOptionMenuViewModel :ViewModel() {
    private val _song = MutableLiveData<Song>()
    private val _optionMenuItems = MutableLiveData<List<MenuItem>>()
    val song: MutableLiveData<Song>
        get() = _song
    val optionMenuItems: MutableLiveData<List<MenuItem>>
        get() = _optionMenuItems

    init {
        _optionMenuItems.value = OptionMenuUtils.songOptionMenuItems
    }

    fun setSong(song: Song){
        _song.postValue(song)
    }
    fun setOptionMenuItems(optionMenuItems: List<MenuItem>){
        _optionMenuItems.postValue(optionMenuItems)
    }


}