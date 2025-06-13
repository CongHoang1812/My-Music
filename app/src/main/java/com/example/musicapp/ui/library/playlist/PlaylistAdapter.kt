package com.example.musicapp.ui.library.playlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicapp.R
import com.example.musicapp.data.model.playlist.Playlist
import com.example.musicapp.data.model.playlist.PlaylistWithSongs
import com.example.musicapp.databinding.ItemPlaylistBinding

class PlaylistAdapter(
    private val listener: OnPlaylistClickListener
) : RecyclerView.Adapter<PlaylistAdapter.ViewHolder>(

) {
    private val _playlists = mutableListOf<PlaylistWithSongs>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemPlaylistBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(_playlists[position])
    }

    override fun getItemCount(): Int {
        return _playlists.size
    }
    fun updatePlaylist(playlists: List<PlaylistWithSongs>){
        val oldSize = _playlists.size
        _playlists.clear()
        _playlists.addAll(playlists)
        if(oldSize>_playlists.size){
            notifyItemRangeRemoved(0, oldSize)
        }
        notifyItemRangeChanged(0, oldSize)

    }


    class ViewHolder(private val binding: ItemPlaylistBinding,
                     private val listener: OnPlaylistClickListener
        ) : RecyclerView.ViewHolder(binding.root){

        fun bind(playlistWithSongs: PlaylistWithSongs) {
            binding.textItemPlaylistName.text = playlistWithSongs.playlist?.name
            val playlistSize = binding.root
                .context
                .getString(R.string.text_playlist_detail_num_of_song, playlistWithSongs.songs.size)
            binding.textItemPlaylistSize.text = playlistSize
            Glide.with(binding.root)
                .load(playlistWithSongs.playlist?.artwork)
                .error(R.drawable.ic_album)
                .into(binding.imagePlaylistAvatar)
            binding.root.setOnClickListener {
                playlistWithSongs.playlist?.let {
                        playlist -> listener.onPlaylistClick(playlist)
                }
            }
            binding.btnItemPlaylistOption.setOnClickListener {
                playlistWithSongs.playlist?.let {
                    listener.onPlaylistMenuOptionClick(it)
                }
            }
        }

    }

    interface OnPlaylistClickListener{
        fun onPlaylistClick(playlist: Playlist)
        fun onPlaylistMenuOptionClick(playlist: Playlist)
    }


}