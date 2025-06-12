package com.example.musicapp.ui.library.favorite

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.musicapp.R
import com.example.musicapp.data.model.song.Song
import com.example.musicapp.databinding.FragmentFavoriteBinding
import com.example.musicapp.ui.home.recommended.SongAdapter

class FavoriteFragment : Fragment() {
    private lateinit var binding:FragmentFavoriteBinding
    private lateinit var adapter: SongAdapter
    private  val favoriteViewModel: FavoriteViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observerData()
    }
    private fun setupView(){
        adapter = SongAdapter(
            object : SongAdapter.OnSongClickListener{
                override fun onClick(song: Song, index: Int) {
                   //todo
                }
            },
            object :SongAdapter.OnSongOptionMenuClickListener{
                override fun onClick(song: Song) {
                    //todo
                }
            }
        )
        binding.includeFavorite.rvSongList.adapter = adapter
    }
    private fun observerData(){
        favoriteViewModel.songs.observe(viewLifecycleOwner){songs->
            adapter.updateSongs(songs)
        }
    }
}