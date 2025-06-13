package com.example.musicapp.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicapp.MusicApplication
import com.example.musicapp.R
import com.example.musicapp.data.model.playlist.Playlist
import com.example.musicapp.data.model.song.Song
import com.example.musicapp.databinding.DialogFragmentSongOptionMenuBinding
import com.example.musicapp.databinding.ItemOptionMenuBinding
import com.example.musicapp.ui.library.playlist.PlaylistViewModel
import com.example.musicapp.utils.OptionMenuUtils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SongOptionMenuDialogFragment : BottomSheetDialogFragment() {
    private lateinit var binding: DialogFragmentSongOptionMenuBinding
    private lateinit var adapter: MenuItemAdapter
    private val viewModel: SongOptionMenuViewModel by activityViewModels()
    private val  songInfoViewModel: DialogSongInfoViewModel by activityViewModels()
    private val playlistViewModel: PlaylistViewModel by activityViewModels {
        val application = requireActivity().application as MusicApplication
        PlaylistViewModel.Factory(application.getPlaylistRepository())
    }
    private var isClicked = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFragmentSongOptionMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        setUpObserver()
    }

    private fun setUpObserver() {
        viewModel.optionMenuItems.observe(viewLifecycleOwner) { items ->
            adapter.updateMenuItems(items)
        }
        viewModel.song.observe(viewLifecycleOwner) {
            showSongInfo(it)
        }
        playlistViewModel.addResult.observe(viewLifecycleOwner) { addResult ->
            if (isClicked) {
                val messageId = if (addResult) {
                    R.string.add_to_playlist_success
                } else {
                    R.string.add_to_playlist_failed
                }
                val playlistName = viewModel.playlistName.value ?: ""
                val message = requireActivity().getString(messageId, playlistName)
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                isClicked = false
            }
        }
    }

    private fun showSongInfo(song: Song) {
        binding.includeSongBottomSheet.textOptionItemSongTitle.text = song.title
        binding.includeSongBottomSheet.textOptionItemSongArtist.text = song.artist
        Glide.with(requireContext()).load(song.image)
            .error(R.drawable.ic_album)
            .into(binding.includeSongBottomSheet.imageOptionSongArtwork)
    }

    private fun setUpView() {
        adapter =
            MenuItemAdapter(listener = object : MenuItemAdapter.OnOptionMenuItemCLickListener {
                override fun onClick(item: MenuItem) {
                    onMenuItemClick(item)
                }
            })
        binding.rvOptionMenu.adapter = adapter

    }
    private fun onMenuItemClick(item: MenuItem){
        when(item.option){
            OptionMenuUtils.OptionMenu.DOWNLOAD -> downloadSong()
            OptionMenuUtils.OptionMenu.VIEW_SONG_INFORMATION -> showDetailSongInfo()
            OptionMenuUtils.OptionMenu.ADD_TO_FAVORITE -> addToFavorite()
            OptionMenuUtils.OptionMenu.ADD_TO_PLAYLIST -> addToPlayList()
            OptionMenuUtils.OptionMenu.ADD_TO_QUEUE -> addToQueue()
            OptionMenuUtils.OptionMenu.VIEW_ARTIST -> viewArtist()
            OptionMenuUtils.OptionMenu.VIEW_ALBUM -> viewAlbum()
            OptionMenuUtils.OptionMenu.BLOCK -> block()
            OptionMenuUtils.OptionMenu.REPORT_ERROR -> report()

        }
    }

    private fun downloadSong() {
        //todo
    }
    private fun showDetailSongInfo(){
        songInfoViewModel.setSong(viewModel.song.value!!)
        DialogSongInfoFragment.newInstance().show(parentFragmentManager, DialogSongInfoFragment.TAG)
    }
    private fun addToFavorite(){

    }

    private fun addToPlayList(){
        val tag = DialogAddSongToPlaylistFragment.TAG
        val dialog = DialogAddSongToPlaylistFragment(
            object : DialogAddSongToPlaylistFragment.OnPlaylistSelectedListener {
                override fun onPlaylistSelected(playlist: Playlist) {
                    val song = viewModel.song.value
                    viewModel.setPlaylistName(playlist.name)
                    playlistViewModel.createPlaylistSongCrossRef(playlist, song)
                    isClicked = true
                }
            }
        )
        dialog.show(requireActivity().supportFragmentManager, tag)
    }
    private fun addToQueue(){

    }
    private fun viewArtist(){

    }
    private fun viewAlbum(){

    }
    private fun block(){

    }
    private fun report(){

    }


    class MenuItemAdapter(
        private val menuItems: MutableList<MenuItem> = mutableListOf<MenuItem>(),
        private val listener: OnOptionMenuItemCLickListener
    ) : RecyclerView.Adapter<MenuItemAdapter.ViewHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): MenuItemAdapter.ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemOptionMenuBinding.inflate(layoutInflater, parent, false)
            return ViewHolder(binding, listener)
        }

        override fun onBindViewHolder(holder: MenuItemAdapter.ViewHolder, position: Int) {
            holder.bind(menuItems[position])
        }

        override fun getItemCount(): Int {
            return menuItems.size
        }

        fun updateMenuItems(items: List<MenuItem>) {
            menuItems.addAll(items)
            notifyItemRangeChanged(0, menuItems.size)
        }

        class ViewHolder(
            private val binding: ItemOptionMenuBinding,
            private val listener: OnOptionMenuItemCLickListener
        ) : RecyclerView.ViewHolder(binding.root) {
            fun bind(item: MenuItem) {
                val title = binding.root.context.getString(item.menuItemTitle)

                binding.textItemMenuTitle.text = title
                Glide.with(binding.root).load(item.iconId).into(binding.imageItemMenuIcon)
                binding.root.setOnClickListener {
                    listener.onClick(item)
                }
            }
        }

        interface OnOptionMenuItemCLickListener {
            fun onClick(item: MenuItem)
        }
    }

    companion object {
        const val TAG = "songOptionMenuDialogFragment"
        val newInstance = SongOptionMenuDialogFragment()
    }

}