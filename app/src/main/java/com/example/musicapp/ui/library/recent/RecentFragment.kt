package com.example.musicapp.ui.library.recent

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.R
import com.example.musicapp.data.model.song.Song
import com.example.musicapp.databinding.FragmentRecentBinding
import com.example.musicapp.ui.PlayerBaseFragment
import com.example.musicapp.ui.detail.DetailFragment
import com.example.musicapp.ui.detail.DetailViewModel
import com.example.musicapp.utils.MusicAppUtils

class RecentFragment : PlayerBaseFragment() {

    private lateinit var binding: FragmentRecentBinding
    private lateinit var adapter: RecentSongAdapter
    private val recentViewModel: RecentViewModel by activityViewModels()
    private val detailViewModel: DetailViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecentBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observeData()
    }

    private fun setupView() {
        adapter = RecentSongAdapter(
            object : RecentSongAdapter.OnSongClickListener {
                override fun onClick(song: Song, index: Int) {
                    playSong(song, index, MusicAppUtils.DefaultPlaylistName.RECENT.value)
                }
            },
            object : RecentSongAdapter.OnSongOptionMenuClickListener {
                override fun onClick(song: Song) {
                    showOptionMenu(song)
                }
            }
        )
        val layoutManager = MyLayoutManager(
            requireContext(),
            3,
            GridLayoutManager.HORIZONTAL,
            false
        )
        binding.rvRecent.adapter = adapter
        binding.rvRecent.layoutManager = layoutManager
        binding.progressRecentHeard.visibility = View.VISIBLE
        binding.textTitleRecent.setOnClickListener {
            navigateToDetailScreen()
        }
        binding.btnMoreRecent.setOnClickListener {
            navigateToDetailScreen()
        }
    }
    private fun navigateToDetailScreen() {
        val playlistName = MusicAppUtils.DefaultPlaylistName.RECENT.value
        val screenName = getString(R.string.title_recent)
        detailViewModel.setScreenName(screenName)
        detailViewModel.setPlaylistName(playlistName)
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment_activity_main, DetailFragment::class.java, null)
            .addToBackStack(null)
            .commit()
    }

    private fun observeData() {
        recentViewModel.recentSongs.observe(viewLifecycleOwner) { songs ->
            adapter.updateSongs(songs)
            detailViewModel.setSong(songs)
            binding.progressRecentHeard.visibility = View.GONE
        }
    }

    internal class MyLayoutManager(
        context: Context,
        spanCount: Int,
        orientation: Int,
        reverseLayout: Boolean
    ) : GridLayoutManager(context, spanCount, orientation, reverseLayout) {
        override fun checkLayoutParams(lp: RecyclerView.LayoutParams): Boolean {
            val deltaX = (MusicAppUtils.DEFAULT_MARGIN_END * MusicAppUtils.DENSITY).toInt()
            lp.width = width - deltaX
            return true
        }
    }
}