package com.example.musicapp.ui.playing

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.ObjectAnimator
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.activityViewModels
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import com.bumptech.glide.Glide
import com.example.musicapp.MusicApplication
import com.example.musicapp.R
import com.example.musicapp.data.model.playlist.Playlist
import com.example.musicapp.data.model.song.Song
import com.example.musicapp.databinding.FragmentMiniPlayerBinding
import com.example.musicapp.ui.viewmodel.MediaPlayerViewModel
import com.example.musicapp.ui.viewmodel.ShareViewModel
import com.example.musicapp.utils.MusicAppUtils


class MiniPlayerFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentMiniPlayerBinding

    private val viewModel: MiniPlayerViewModel by activityViewModels {
        val application = requireActivity().application as MusicApplication
        val repository = application.getSongRepository()
        MiniPlayerViewModel.Factory(repository)
    }
    private var mediaController: MediaController? = null
    private lateinit var pressedAnimator: Animator
    private lateinit var rotationAnimator: ObjectAnimator
    private lateinit var shareViewModel: ShareViewModel
    private var currentFraction: Float = 0f
    private val nowPlayingActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            currentFraction = result.data
                ?.getFloatExtra(MusicAppUtils.EXTRA_CURRENT_FRACTION, 0f) ?: 0f
            mediaController?.let {
                if (it.isPlaying) {
                    rotationAnimator.start()
                    rotationAnimator.setCurrentFraction(currentFraction)
                    currentFraction = 0f
                } else {
                    rotationAnimator.setCurrentFraction(currentFraction)
                }
            }
        }
    }


    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMiniPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpView()
        setUpViewModel()
        setUpAnimator()
        setUpMediaController()
        setUpObserve()
    }

    override fun onResume() {
        super.onResume()
        val currentPlayingSong = shareViewModel.playingSong.value?.song
        currentPlayingSong?.let {
            val songId = it.id
            viewModel.getCurrentPlayingSong(songId)
            viewModel.currentPlayingSong.observe(viewLifecycleOwner) { song ->
                if (song != null) {
                    updateFavoriteStatus(song)
                }
            }
        }
    }

    private fun setUpViewModel() {
        shareViewModel = ShareViewModel.instance
    }

    private fun setUpAnimator() {
        pressedAnimator = AnimatorInflater.loadAnimator(requireContext(), R.animator.button_pressed)
        rotationAnimator = ObjectAnimator
            .ofFloat(binding.imageMiniPlayerArtwork, "rotation", 0f, 360f)
        rotationAnimator.interpolator = LinearInterpolator()
        rotationAnimator.duration = 12000
        rotationAnimator.repeatCount = ObjectAnimator.INFINITE
        rotationAnimator.repeatMode = ObjectAnimator.RESTART

    }

    private fun setUpView() {
        binding.btnMiniPlayerFavorite.setOnClickListener(this)
        binding.btnMiniPlayerPause.setOnClickListener(this)
        binding.btnMiniPlayerSkipNext.setOnClickListener(this)
        binding.root.setOnClickListener {
            navigateToNowPlaying()
        }
    }

    private fun navigateToPlaying() {

    }

    private fun setUpMediaController() {
        MediaPlayerViewModel.instance.mediaController.observe(viewLifecycleOwner) { controller ->
            controller?.let {
                mediaController = it
            }
            setUpListener()
            setupObserveForMediaController()
        }
    }

    private fun setupObserveForMediaController() {
        mediaController?.let {
            viewModel.mediaItems.observe(viewLifecycleOwner) { mediaItems ->
                it.setMediaItems(mediaItems)
            }

            shareViewModel.indexToPlay.observe(viewLifecycleOwner) { index ->
                val playingSong = shareViewModel.playingSong.value
                var currentPlaylist: Playlist? = null
                if(playingSong!=null){
                    currentPlaylist = playingSong.playlist
                }
                val playlistToPlay = shareViewModel.currentPlaylist.value
                // th1: the same playlist, the same index -> khong phat lai bai hat ma tiep tuc
                // th2: khac playlist, cung index -> phat bai hat tu dau
                val condition1 = it.mediaItemCount > index && it.currentMediaItemIndex != index
                val condition2 = playlistToPlay != null && it.currentMediaItemIndex == index
                        && playlistToPlay.id  != currentPlaylist?.id
                if (index != -1 && (condition1 ||condition2)) {
                    it.seekTo(index, 0)
                    it.prepare()
                                     it.play()
                }
            }
        }
    }

    private fun setUpListener() {
        mediaController?.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                viewModel.setPlayingState(isPlaying)
            }
        })
    }

    private fun setUpObserve() {
        shareViewModel.playingSong.observe(viewLifecycleOwner) {
            it.song?.let { song -> showSongInfo(song) }
        }
        shareViewModel.currentPlaylist.observe(viewLifecycleOwner) {playlist->
            val playingSong = shareViewModel.playingSong.value
            var currentPlaylist: Playlist? = null
            if(playingSong!=null){
                currentPlaylist = playingSong.playlist
            }
            if(playlist.mediaItem.isNotEmpty() && (currentPlaylist == null || playlist.id != currentPlaylist.id) ){
                viewModel.setMediaItem(playlist.mediaItem)
            }

        }
        viewModel.isPlaying.observe(viewLifecycleOwner){
            if(it){
                binding.btnMiniPlayerPause.setImageResource(R.drawable.ic_pause_circle)
                if(rotationAnimator.isPaused){
                    if(currentFraction!=0f){
                        rotationAnimator.start()
                        rotationAnimator.setCurrentFraction(currentFraction)
                    }else{
                        rotationAnimator.resume()
                    }
                }else if(!rotationAnimator.isRunning){
                    rotationAnimator.start()
                }
            }
        }


    }

    private fun showSongInfo(song: Song) {
        binding.textMiniPlayerTitle.text = song.title
        binding.textMiniPlayerArtist.text = song.artist
        Glide.with(requireActivity())
            .load(song.image)
            .error(R.drawable.ic_album)
            .circleCrop()
            .into(binding.imageMiniPlayerArtwork)
        updateFavoriteStatus(song)
    }

    override fun onClick(v: View?) {
        pressedAnimator.setTarget(v)
        pressedAnimator.start()
        if (v == binding.btnMiniPlayerPause) {
            mediaController?.let {
                if (it.isPlaying) {
                    it.pause()
                } else {
                    it.play()
                }
            }
        } else if (v == binding.btnMiniPlayerSkipNext) {
            mediaController?.let {
                if (it.hasNextMediaItem()) {
                    it.seekToNextMediaItem()
                    rotationAnimator.end()
                }
            }
        } else if (v == binding.btnMiniPlayerFavorite) {
            setUpFavorite()
        }
    }

    private fun setUpFavorite() {
        val playingSong = shareViewModel.playingSong.value
        playingSong?.let {
            val song = it.song
            song!!.favorite = !song.favorite
            updateFavoriteStatus(song)
            shareViewModel.updateFavoriteStatus(song)
        }
    }

    private fun updateFavoriteStatus(song: Song) {
        val favoriteIcon = if (song.favorite) {
            R.drawable.ic_favorite_on
        } else {
            R.drawable.ic_favorite_off

        }
        binding.btnMiniPlayerFavorite.setImageResource(favoriteIcon)
    }

    private fun navigateToNowPlaying() {
        val intent = Intent(requireContext(), NowPlayingActivity::class.java).apply {
            rotationAnimator.pause()
            putExtra(MusicAppUtils.EXTRA_CURRENT_FRACTION, rotationAnimator.animatedFraction)
        }
        val options = ActivityOptionsCompat
            .makeCustomAnimation(requireContext(), R.anim.slide_up, R.anim.fade_out)
        nowPlayingActivityLauncher.launch(intent, options)
    }
}


