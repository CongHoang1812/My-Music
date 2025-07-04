package com.example.musicapp.ui.library.playlist.creation

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.musicapp.MusicApplication
import com.example.musicapp.R
import com.example.musicapp.ui.library.playlist.PlaylistViewModel
import com.google.android.material.textfield.TextInputEditText


class DialogPlaylistCreationFragment(
    private val listener: OnClickListener,
    private val textChangeListener: OnTextChangeListener
) : DialogFragment() {
    private val playlistViewModel: PlaylistViewModel by activityViewModels {
        val application = requireActivity().application as MusicApplication
        PlaylistViewModel.Factory(application.getPlaylistRepository())
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val layoutInflater = requireActivity().layoutInflater
        val rootView = layoutInflater.inflate(R.layout.fragment_dialog_playlist_creation, null)
        val editPlaylistName = rootView.findViewById<TextInputEditText>(R.id.edit_playlist_name)
        builder.setView(rootView)
            .setTitle(getString(R.string.title_create_playlist))
            .setPositiveButton(getString(R.string.action_create)) { _, _ ->
                setupPositiveButton(editPlaylistName)
            }
            .setNegativeButton(getString(R.string.action_cancel)) { _, _ ->
                dismiss()
            }
        setupEditTextListener(editPlaylistName)
        playlistViewModel.findPlaylistByName("")
        observeData(editPlaylistName)
        return builder.create()
    }
    private fun setupPositiveButton(editPlaylistName: TextInputEditText) {
        if (editPlaylistName.text != null) {
            val playlistName = editPlaylistName.text.toString().trim()
            if (playlistName.isNotEmpty()) {
                if (editPlaylistName.error == null) {
                    listener.onClick(playlistName)
                }
            } else {
                val message = getString(R.string.error_empty_playlist_name)
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupEditTextListener(editPlaylistName: TextInputEditText) {
        editPlaylistName.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                requireActivity().window
                    .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
            }
        }
        editPlaylistName.doOnTextChanged { text, _, _, _ ->
            textChangeListener.onTextChange(text.toString())
        }
    }


    private fun observeData(editPlaylistName: TextInputEditText) {
        playlistViewModel.playlist.observe(requireActivity()) { playlist ->
            if (playlist != null) {
                val message = editPlaylistName.context.getString(R.string.error_playlist_exists)
                editPlaylistName.error = message
            } else {
                editPlaylistName.error = null
            }
        }
    }
    interface OnClickListener {
        fun onClick(playlistName: String)
    }
    interface OnTextChangeListener {
        fun onTextChange(playlistName: String)
    }

    companion object {
        const val TAG = "DialogPlaylistCreationFragment"
    }

}