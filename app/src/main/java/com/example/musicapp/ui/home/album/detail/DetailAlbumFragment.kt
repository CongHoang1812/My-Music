package com.example.musicapp.ui.home.album.detail

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.musicapp.R
import com.example.musicapp.data.model.song.Song
import com.example.musicapp.databinding.FragmentDetaiAlbumlBinding
import com.example.musicapp.ui.PlayerBaseFragment
import com.example.musicapp.ui.home.recommended.SongAdapter
import com.example.musicapp.ui.viewmodel.ShareViewModel

class DetailAlbumFragment : PlayerBaseFragment() {
    private lateinit var binding : FragmentDetaiAlbumlBinding
    private lateinit var adapter: SongAdapter
    private val detailAlbumViewModel : DetailAlbumViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetaiAlbumlBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        setUpViewModel()
    }

    private fun setUpViewModel() {
        detailAlbumViewModel.album.observe(viewLifecycleOwner){ album ->
            binding.includeAlbumDetail.textPlaylistDetailTitle.text = album.name
            var text = getString(R.string.text_playlist_detail_num_of_song, album.size)
            binding.includeAlbumDetail.textPlaylistDetailNumOfSong.text = text;
            Glide.with(binding.root)
                .load(album.artwork)
                .error(R.drawable.baseline_album_24)
                .into(binding.includeAlbumDetail.imagePlaylistArtwork)

        }

        detailAlbumViewModel.songs.observe(viewLifecycleOwner){
            adapter.updateSongs(it)
        }
    }

    private fun setUpView() {
        binding.includeAlbumDetail.toolbarPlaylistDetails.setNavigationOnClickListener{
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        adapter = SongAdapter(
            object : SongAdapter.OnSongClickListener{
                override fun onClick(song: Song, index: Int) {
                    detailAlbumViewModel.playlist.observe(viewLifecycleOwner){playlist->
                        ShareViewModel.instance.addPlaylist(playlist)
                        playSong(song, index, playlist.name)
                    }
                }

            },
            object :SongAdapter.OnSongOptionMenuClickListener{
                override fun onClick(song: Song) {
                    showOptionMenu(song)
                }
            }
        )
        binding.includeAlbumDetail.includeSongList.rvSongList.adapter = adapter
    }
}