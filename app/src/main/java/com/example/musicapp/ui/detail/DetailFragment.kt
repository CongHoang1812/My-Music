package com.example.musicapp.ui.detail

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.musicapp.R
import com.example.musicapp.data.model.song.Song
import com.example.musicapp.databinding.FragmentDetailBinding
import com.example.musicapp.ui.PlayerBaseFragment
import com.example.musicapp.ui.home.recommended.SongAdapter

class DetailFragment : PlayerBaseFragment() {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var adapter: SongAdapter
    private val detailViewModel: DetailViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observerData()
    }
    private fun setupView(){
        binding.toolbarDetailSongList.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        adapter = SongAdapter(
            object : SongAdapter.OnSongClickListener {
                override fun onClick(song: Song, index: Int) {
                    val playlistName = detailViewModel.playlistName.value ?: ""
                    playSong(song, index, playlistName)
                }
            },
            object : SongAdapter.OnSongOptionMenuClickListener {
                override fun onClick(song: Song) {
                    showOptionMenu(song)
                }
            }
        )
        binding.includeSongList.rvSongList.adapter = adapter
    }
    private fun observerData(){
        detailViewModel.songs.observe(viewLifecycleOwner) { songs ->
            adapter.updateSongs(songs)
        }
        detailViewModel.screenName.observe(viewLifecycleOwner) { screenName ->
            binding.textTitleDetailSongList.text = screenName
        }
        detailViewModel.playlistName.observe(viewLifecycleOwner) { playlistName ->
            // todo
        }
    }

}