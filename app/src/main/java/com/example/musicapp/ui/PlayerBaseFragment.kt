package com.example.musicapp.ui

import android.content.Intent
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.musicapp.R
import com.example.musicapp.data.model.song.Song
import com.example.musicapp.ui.dialog.SongOptionMenuDialogFragment
import com.example.musicapp.ui.dialog.SongOptionMenuViewModel
import com.example.musicapp.ui.playing.NowPlayingActivity
import com.example.musicapp.ui.viewmodel.PermissionViewModel
import com.example.musicapp.ui.viewmodel.ShareViewModel
import com.example.musicapp.utils.MusicAppUtils

open class PlayerBaseFragment : Fragment() {
    protected fun playSong(song: Song, index: Int, playlistName: String) {
        val isPermissionGranted = PermissionViewModel.instance.permissionGranted.value
        if (isPermissionGranted != null && isPermissionGranted) {
            doNavigate(index, playlistName)
        } else if (!PermissionViewModel.isRegistered) {
            PermissionViewModel.instance
                .permissionGranted
                .observe(requireActivity()) {
                    if (it) {
                        doNavigate(index, playlistName)
                    }
                }
            PermissionViewModel.isRegistered = true
        }
    }
    private fun doNavigate(index: Int, playlistName: String) {
        val sharedViewModel = ShareViewModel.instance
        sharedViewModel.setCurrentPlaylist(playlistName)
        sharedViewModel.setIndexToPlay(index)
        Intent(requireContext(), NowPlayingActivity::class.java).apply {
            val options = ActivityOptionsCompat.makeCustomAnimation(requireContext(), R.anim.slide_up, R.anim.fade_out)
            startActivity(this, options.toBundle())
        }
    }

    protected fun showOptionMenu(song: Song){
        val menuDialogFragment = SongOptionMenuDialogFragment.newInstance
        val menuDialogViewModel  : SongOptionMenuViewModel by activityViewModels()
        menuDialogViewModel.setSong(song)
        menuDialogFragment.show(parentFragmentManager, SongOptionMenuDialogFragment.TAG)

    }

}