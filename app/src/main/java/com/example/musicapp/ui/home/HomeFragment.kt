package com.example.musicapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.musicapp.MusicApplication
import com.example.musicapp.databinding.FragmentHomeBinding
import com.example.musicapp.ui.home.album.AlbumHotViewModel
import com.example.musicapp.ui.home.recommended.RecommendedViewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by activityViewModels{
        val application = requireActivity().application as MusicApplication
        HomeViewModel.Factory(application.getSongRepository())
    }
    private val albumViewModel: AlbumHotViewModel by activityViewModels()
    private val songViewModel: RecommendedViewModel by activityViewModels()
    private var isObserver: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("==>", "HomeFragment: onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isObserver) {
            setUpObserver()
            isObserver = true
        }

    }


    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("==>", "HomeFragment: onDestroyView")
    }

    private fun setUpObserver() {
        homeViewModel.albums.observe(viewLifecycleOwner) {
            albumViewModel.setAlbums(it)
        }
        homeViewModel.songs.observe(viewLifecycleOwner) {
            homeViewModel.saveSongsToDB()
        }
        homeViewModel.localSongs.observe(viewLifecycleOwner) {
            if(it.isEmpty()){
                homeViewModel.songs.observe(viewLifecycleOwner){remoteSongs ->
                    songViewModel.setSongs(remoteSongs)
                }
            }else{
                songViewModel.setSongs(it)
            }
        }

    }
}