package com.example.musicapp.ui.discovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.musicapp.MusicApplication

import com.example.musicapp.databinding.FragmentDiscoveryBinding
import com.example.musicapp.ui.discovery.artist.ArtistViewModel
import com.example.musicapp.ui.discovery.foryou.ForYouViewModel
import com.example.musicapp.ui.discovery.mostheard.MostHeardViewModel

class DiscoveryFragment : Fragment() {
    private lateinit var binding: FragmentDiscoveryBinding
    private val artistViewModel : ArtistViewModel by activityViewModels()
    private val mostHeardViewModel : MostHeardViewModel by activityViewModels()
    private val forYouViewModel : ForYouViewModel by activityViewModels()
    private val discoveryViewModel: DiscoveryViewModel by activityViewModels{
        val application = requireActivity().application as MusicApplication
        DiscoveryViewModel.Factory(application.songRepository,application.artistRepository)

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDiscoveryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        if (savedInstanceState != null) {
            binding.scrollViewDiscovery.post {
                binding.scrollViewDiscovery.scrollY = savedInstanceState.getInt(SCROLL_POSITION)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SCROLL_POSITION, binding.scrollViewDiscovery.scrollY)
    }


    private fun observeData() {
        discoveryViewModel.artists.observe(viewLifecycleOwner) {artists ->
            discoveryViewModel.saveArtistToDB()
            discoveryViewModel.saveArtistSongCrossRef(artists)

        }
        discoveryViewModel.top15Artists.observe(viewLifecycleOwner) { artists ->
            artistViewModel.setArtists(artists)
        }

        discoveryViewModel.top15MostHeardSongs.observe(viewLifecycleOwner) { songs ->
            mostHeardViewModel.setSongs(songs)
        }
        discoveryViewModel.top15ForYouSongs.observe(viewLifecycleOwner) { songs ->
            forYouViewModel.setSongs(songs)}
    }

    companion object {
        const val  SCROLL_POSITION = "com.example.musicapp.ui.discovery.scroll_position"
    }
}