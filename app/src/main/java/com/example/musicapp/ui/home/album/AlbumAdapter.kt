package com.example.musicapp.ui.home.album

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicapp.R
import com.example.musicapp.data.model.album.Album
import com.example.musicapp.databinding.ItemAlbumBinding

class AlbumAdapter(
    private val listener: OnAlbumClickListener
) : RecyclerView.Adapter<AlbumAdapter.ViewHolder>(){
    private val albums = mutableListOf<Album>()


    class ViewHolder(
        private val binding: ItemAlbumBinding,
        private val listener: OnAlbumClickListener
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(album: Album){
            binding.textAlbumItemName.text = album.name
            Glide.with(binding.root.context).load(album.artwork)
                .error(androidx.media3.session.R.drawable.media3_icon_album)
                .into(binding.imageAlbumItem)
            binding.root.setOnClickListener{
                listener.onAlbumClick(album)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemAlbumBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(albums[position])
    }

    override fun getItemCount(): Int {
        return albums.size
    }
    fun updateAlbums(newAlbums: List<Album>?){
       newAlbums?.let {
           val oldSize = albums.size
           albums.clear()
           albums.addAll(newAlbums)
           if(oldSize > albums.size){
               notifyItemRangeChanged(0, oldSize)
           }
           notifyItemRangeChanged(0, albums.size)
       }

    }

    interface OnAlbumClickListener{
        fun onAlbumClick(album: Album);
    }
}