package com.example.musicapp.ui.discovery.artist.detail
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.musicapp.MusicApplication
import com.example.musicapp.R
import com.example.musicapp.data.model.artist.Artist
import com.example.musicapp.data.model.playlist.Playlist
import com.example.musicapp.data.model.song.Song
import com.example.musicapp.databinding.FragmentArtistDetailBinding
import com.example.musicapp.ui.PlayerBaseFragment
import com.example.musicapp.ui.home.recommended.SongAdapter
import com.example.musicapp.ui.viewmodel.ShareViewModel

class ArtistDetailFragment : PlayerBaseFragment() {
    private lateinit var binding: FragmentArtistDetailBinding
    private lateinit var adapter: SongAdapter
    private val artistDetailViewModel: ArtistDetailViewModel by activityViewModels {
        val application = requireActivity().application as MusicApplication
        ArtistDetailViewModel.Factory(application.artistRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArtistDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observeData()
    }

    private fun setupView() {
        binding.toolbarArtistDetail.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        adapter = SongAdapter(
            object : SongAdapter.OnSongClickListener {
                override fun onClick(song: Song, index: Int) {
                    prepare(song, index)
                }
            },
            object : SongAdapter.OnSongOptionMenuClickListener {
                override fun onClick(song: Song) {
                    showOptionMenu(song)
                }
            }
        )
        binding.includeDetailArtistSongList.rvSongList.adapter = adapter
    }

    private fun prepare(song: Song, index: Int) {
        val artist = artistDetailViewModel.artist.value
        val playlistName = artist?.name ?: ""
        val playlist = Playlist(name = playlistName)
        playlist.id = -1
        val songs = artistDetailViewModel.artistWithSongs.value?.songs
        songs?.let {
            playlist.updateSongList(it)
            ShareViewModel.instance.addPlaylist(playlist)
            playSong(song, index, playlistName)
        }
    }

    private fun observeData() {
        artistDetailViewModel.artistWithSongs.observe(viewLifecycleOwner) { artistWithSongs ->
            adapter.updateSongs(artistWithSongs.songs)
        }
        artistDetailViewModel.artist.observe(viewLifecycleOwner) { artist ->
            showArtistInfo(artist)
        }
    }

    private fun showArtistInfo(artist: Artist) {
        binding.textDetailArtistName.text = getString(R.string.text_artist_name, artist.name)
        binding.textDetailInterested.text =
            getString(R.string.text_number_subscriber, artist.interested)
        val interested = if (artist.isCareAbout) "YES" else "NO"
        binding.textArtistDetailYourInterest.text = getString(R.string.text_artist_name, interested)
        Glide.with(binding.root)
            .load(artist.avatar)
            .error(R.drawable.ic_artist)
            .circleCrop()
            .into(binding.imageArtistDetailAvatar)
    }
}