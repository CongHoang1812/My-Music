package com.example.musicapp.ui.home.album.more

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicapp.R
import com.example.musicapp.data.model.album.Album
import com.example.musicapp.databinding.ItemMoreAlbumBinding
import com.example.musicapp.ui.home.album.AlbumAdapter

class MoreAlbumAdapter(
    private val listener: AlbumAdapter.OnAlbumClickListener
) : RecyclerView.Adapter<MoreAlbumAdapter.ViewHolder>() {
    private val albums = mutableListOf<Album>()
    class ViewHolder(
         private val binding: ItemMoreAlbumBinding,
         private val listener: AlbumAdapter.OnAlbumClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album){
            binding.textNameItemMoreAlbum.text = album.name;
            Glide.with(itemView.context)
                .load(album.artwork)
                .error(R.drawable.baseline_album_24)
                .into(binding.imageItemMoreAlbum)
            binding.root.setOnClickListener {
                listener.onAlbumClick(album);
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoreAlbumAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMoreAlbumBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: MoreAlbumAdapter.ViewHolder, position: Int) {
        holder.bind(albums[position])
    }

    override fun getItemCount(): Int {
        return albums.size
    }
    fun updateAlbums(newAlbums: List<Album>?){
        newAlbums?.let {
            val oldSize = albums.size
            albums.clear()
            albums.addAll(it)
            if(oldSize > albums.size){
                notifyItemRangeChanged(0, oldSize)
            }
            notifyItemRangeChanged(0, albums.size)
        }
    }
}