package com.example.musicapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PermissionViewModel private constructor(

): ViewModel(){
    private val _permissionAsked = MutableLiveData<Boolean>()
    private val _permissionGranted = MutableLiveData<Boolean>()

    val permissionAsked: MutableLiveData<Boolean>
        get() = _permissionAsked
    val permissionGranted: MutableLiveData<Boolean>
        get() = _permissionGranted

    fun askPermission(){
        _permissionAsked.value = true
    }
    fun grantPermission(){
        _permissionGranted.value = true
    }
    companion object{
        var isRegistered: Boolean = false
        val instance = PermissionViewModel()
    }
}