package com.example.musicapp.ui.home.recommended

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicapp.R
import com.example.musicapp.data.model.song.Song
import com.example.musicapp.databinding.FragmentAlbumHotBinding
import com.example.musicapp.databinding.ItemSongBinding
import com.example.musicapp.ui.viewmodel.PermissionViewModel

class SongAdapter(
    private val onSongClickListener: OnSongClickListener,
    private val onSongOptionMenuClickListener: OnSongOptionMenuClickListener
) : RecyclerView.Adapter<SongAdapter.ViewHolder>(){
    private val songs : MutableList<Song> = mutableListOf()

    class ViewHolder(
        private val binding: ItemSongBinding,
        private val onSongClickListener: OnSongClickListener,
        private val onSongOptionMenuClickListener: OnSongOptionMenuClickListener
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(song: Song, index: Int){
            binding.textItemSongTitle.text = song.title
            binding.textItemSongArtist.text = song.artist
            Glide.with(binding.root)
                .load(song.image)
                .error(R.drawable.ic_library_music)
                .into(binding.imageItemSong)
            binding.root.setOnClickListener{
                val isGranted = PermissionViewModel.instance.permissionGranted.value
                if(isGranted == null || !isGranted){
                    PermissionViewModel.instance.askPermission()
                }
                onSongClickListener.onClick(song, index)
            }
            binding.btnItemSongOption.setOnClickListener{
                onSongOptionMenuClickListener.onClick(song)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemSongBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding, onSongClickListener, onSongOptionMenuClickListener)
    }

    fun updateSongs(newSongs: List<Song>?){
        newSongs?.let {
            val oldSize = songs.size
            songs.clear()
            songs.addAll(newSongs)
            if(oldSize > songs.size){
                notifyItemRangeRemoved(songs.size, oldSize)
            }
            notifyItemRangeChanged(0, songs.size)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(songs[position], position)
    }

    override fun getItemCount(): Int {
        return songs.size
    }

    interface OnSongClickListener{
        fun onClick(song: Song, index: Int)
    }
    interface OnSongOptionMenuClickListener{
        fun onClick(song: Song)
    }
}