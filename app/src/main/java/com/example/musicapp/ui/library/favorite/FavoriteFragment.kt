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
import com.example.musicapp.ui.PlayerBaseFragment
import com.example.musicapp.ui.detail.DetailFragment
import com.example.musicapp.ui.detail.DetailViewModel
import com.example.musicapp.ui.home.recommended.SongAdapter
import com.example.musicapp.utils.MusicAppUtils

class FavoriteFragment : PlayerBaseFragment() {
    private lateinit var binding:FragmentFavoriteBinding
    private lateinit var adapter: SongAdapter
    private  val favoriteViewModel: FavoriteViewModel by activityViewModels()
    private val detailViewModel: DetailViewModel by activityViewModels()


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
                    playSong(song, index, MusicAppUtils.DefaultPlaylistName.FAVORITES.value)
                }
            },
            object :SongAdapter.OnSongOptionMenuClickListener{
                override fun onClick(song: Song) {
                    showOptionMenu(song)
                }
            }
        )
        binding.includeFavorite.rvSongList.adapter = adapter
        binding.textTitleFavorite.setOnClickListener {
            navigateToDetailScreen()
        }
        binding.btnMoreFavorite.setOnClickListener {
            navigateToDetailScreen()
        }
    }
    private fun navigateToDetailScreen() {
        val playlistName = MusicAppUtils.DefaultPlaylistName.FAVORITES.value
        val screenName = getString(R.string.title_favorite)
        detailViewModel.setScreenName(screenName)
        detailViewModel.setPlaylistName(playlistName)
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment_activity_main, DetailFragment::class.java, null)
            .addToBackStack(null)
            .commit()
    }
    private fun observerData(){
        favoriteViewModel.songs.observe(viewLifecycleOwner){songs->
            adapter.updateSongs(songs)
            detailViewModel.setSong(songs)
        }
    }
}