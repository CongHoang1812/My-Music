package com.example.musicapp.ui.home.recommended.more

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.musicapp.R
import com.example.musicapp.data.model.song.Song
import com.example.musicapp.databinding.FragmentMoreAlbumBinding
import com.example.musicapp.databinding.FragmentMoreRecommendedBinding
import com.example.musicapp.ui.PlayerBaseFragment
import com.example.musicapp.ui.home.recommended.SongAdapter
import com.example.musicapp.utils.MusicAppUtils

class MoreRecommendedFragment : PlayerBaseFragment() {
    private lateinit var binding : FragmentMoreRecommendedBinding
    private val moreRecommendedViewModel : MoreRecommendedViewModel by activityViewModels()
    private lateinit var adapter: SongAdapter


    private val viewModel: MoreRecommendedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoreRecommendedBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setUpObserver()

    }
    private fun setupView() {
       adapter = SongAdapter(
           object : SongAdapter.OnSongClickListener {
               override fun onClick(song: Song, index: Int) {
                    val playlistName = MusicAppUtils.DefaultPlaylistName.RECOMMENDED.value
                   playSong(song, index, playlistName)
               }
           },
           object : SongAdapter.OnSongOptionMenuClickListener {
               override fun onClick(song: Song) {
                   showOptionMenu(song)
               }
           }
       )
        binding.includeMoreRecommended.rvSongList.adapter = adapter
        binding.toolbarMoreRecommended.setNavigationOnClickListener{
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

    }
    private fun setUpObserver() {
        moreRecommendedViewModel.recommendedSong.observe(viewLifecycleOwner) { songs ->
            adapter.updateSongs(songs)
        }
    }
}