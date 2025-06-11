package com.example.musicapp.utils

import com.example.musicapp.R
import com.example.musicapp.ui.dialog.MenuItem

object OptionMenuUtils {
    private val menuItems = mutableListOf<MenuItem>()
    init {
        createSongOptionMenuItem()
    }
    private fun createSongOptionMenuItem() {
        menuItems.add(MenuItem(OptionMenu.DOWNLOAD, R.drawable.ic_download, R.string.download))
        menuItems.add(MenuItem(OptionMenu.ADD_TO_FAVORITE, R.drawable.ic_favorite, R.string.add_to_favorite))
        menuItems.add(MenuItem(OptionMenu.ADD_TO_PLAYLIST, R.drawable.ic_playlist, R.string.add_to_playlist))
        menuItems.add(MenuItem(OptionMenu.ADD_TO_QUEUE, R.drawable.ic_queue, R.string.add_to_queue))
        menuItems.add(MenuItem(OptionMenu.VIEW_ALBUM, R.drawable.ic_album, R.string.view_album))
        menuItems.add(MenuItem(OptionMenu.VIEW_ARTIST, R.drawable.ic_artist, R.string.view_artist))
        menuItems.add(MenuItem(OptionMenu.BLOCK, R.drawable.ic_block, R.string.block))
        menuItems.add(MenuItem(OptionMenu.REPORT_ERROR, R.drawable.ic_report, R.string.report_error))
        menuItems.add(MenuItem(OptionMenu.VIEW_SONG_INFORMATION, R.drawable.ic_information, R.string.view_song_information))

    }
    @JvmStatic
    val songOptionMenuItems: List<MenuItem>
        get() = menuItems
    enum class  OptionMenu(val value: String){
        DOWNLOAD(value = "download"),
        ADD_TO_FAVORITE("add_favorite"),
        ADD_TO_PLAYLIST("add_playlist"),
        ADD_TO_QUEUE("add_queue"),
        VIEW_ALBUM("view_album"),
        VIEW_ARTIST("view_artist"),
        BLOCK("block"),
        REPORT_ERROR("report_error"),
        VIEW_SONG_INFORMATION("view_song_information")
    }
}