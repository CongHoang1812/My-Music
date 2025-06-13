package com.example.musicapp.ui.home.recommended

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.musicapp.R
import com.example.musicapp.data.model.song.Song
import com.example.musicapp.databinding.FragmentRecommendedBinding
import com.example.musicapp.ui.home.recommended.more.MoreRecommendedFragment
import com.example.musicapp.ui.home.recommended.more.MoreRecommendedViewModel
import com.example.musicapp.ui.PlayerBaseFragment
import com.example.musicapp.ui.detail.DetailFragment
import com.example.musicapp.ui.detail.DetailViewModel
import com.example.musicapp.ui.viewmodel.ShareViewModel
import com.example.musicapp.utils.MusicAppUtils

class RecommendedFragment : PlayerBaseFragment() {


    private lateinit var binding: FragmentRecommendedBinding
    private lateinit var adapter: SongAdapter
    private val moreRecommendedViewModel: MoreRecommendedViewModel by activityViewModels()
    private val detailViewModel: DetailViewModel by activityViewModels()
    private val recommendedViewModel: RecommendedViewModel by activityViewModels()
    //private val miniPlayerViewModel: MiniPlayerViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecommendedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        setUpViewModel()
    }

    private fun setUpView() {
        adapter = SongAdapter(
            object : SongAdapter.OnSongClickListener {
                override fun onClick(song: Song, index: Int) {
                    val playlistName = MusicAppUtils.DefaultPlaylistName.RECOMMENDED.value
                    playSong(song, index, playlistName )
                }

            },
            object : SongAdapter.OnSongOptionMenuClickListener {
                override fun onClick(song: Song) {
                    showOptionMenu(song)
                }
            }
        )
        binding.includeSongList.rvSongList.adapter = adapter
        binding.btnMoreRecommended.setOnClickListener {
            navigateToDetailScreen()
        }
        binding.textTitleRecommended.setOnClickListener {
            navigateToDetailScreen()
        }
    }
    private fun navigateToDetailScreen() {
        val playlistName = "recommended"
        val screenName = getString(R.string.title_recommended)
        detailViewModel.setScreenName(screenName)
        detailViewModel.setPlaylistName(playlistName)
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment_activity_main, DetailFragment::class.java, null)
            .addToBackStack(null)
            .commit()
    }


//    private fun navigateToMoreRecommended() {
//        requireActivity().supportFragmentManager.beginTransaction()
//            .replace(
//                R.id.nav_host_fragment_activity_main,
//                MoreRecommendedFragment::class.java,
//                null
//            )
//            .addToBackStack(null)
//            .commit()
//    }

    private fun setUpViewModel() {
        recommendedViewModel.songs.observe(viewLifecycleOwner) { songs ->
            detailViewModel.setSong(songs)
            adapter.updateSongs(songs.subList(0, 16))
            moreRecommendedViewModel.setRecommendedSong(songs)
            val playListName = MusicAppUtils.DefaultPlaylistName.RECOMMENDED.value
            ShareViewModel.instance.setUpPlayList(songs, playListName)
        }
    }
}