package com.example.musicapp

import android.util.DisplayMetrics
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.musicapp.databinding.ActivityMainBinding
import com.example.musicapp.ui.viewmodel.ShareViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.musicapp.ui.home.HomeViewModel
import com.example.musicapp.ui.viewmodel.PermissionViewModel
import com.example.musicapp.utils.MusicAppUtils
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    private var currentSongLoaded = false

    private val sharedViewModel: ShareViewModel by viewModels {
        val application = application as MusicApplication
        ShareViewModel.Factory(
            application.getRecentSongRepository(),
            application.getSongRepository()

        )
    }
    private val homeViewModel: HomeViewModel by viewModels{
        val application = application as MusicApplication
        HomeViewModel.Factory(application.getSongRepository())
    }
    private val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){isGranted->
        if(!isGranted){
            val snackBar = Snackbar.make(
                binding.root.rootView,
                getString(R.string.permission_denied)
                , Snackbar.LENGTH_LONG

            )
            snackBar.setAnchorView(R.id.nav_view)
        }else{
            PermissionViewModel.instance.grantPermission()
        }
    }
    private val shareViewModel: ShareViewModel by viewModels{
        val application = application as MusicApplication
        ShareViewModel.Factory(application.getRecentSongRepository(), application.getSongRepository())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupBottomNav()
        setupComponents()
        observeData()

    }

    override fun onResume() {
        super.onResume()
        shareViewModel.playingSong.observe(this){
            if(it.song!=null){
                binding.fcvMiniPlayer.visibility = VISIBLE
            }else{
                binding.fcvMiniPlayer.visibility = GONE
            }
        }
    }


    private fun observeData() {
        sharedViewModel.isReady.observe(this){ready->
            if(ready && !currentSongLoaded){
                loadPreviousSessionSong()
                currentSongLoaded = true
            }
        }
        PermissionViewModel.instance
            .permissionAsked
            .observe(this) {
                if (it) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        checkPostNotificationPermission()
                    }
                }
            }
    }
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkPostNotificationPermission() {
        val permission = android.Manifest.permission.POST_NOTIFICATIONS
        val isPermissionGranted = ActivityCompat
            .checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
        if (isPermissionGranted) {
            PermissionViewModel.instance.grantPermission()
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            val snackBar = Snackbar.make(
                binding.root.rootView,
                getString(R.string.permission_denied),
                Snackbar.LENGTH_LONG
            )
            snackBar.setAction(R.string.action_agree) {
                permissionLauncher.launch(permission)
            }
            snackBar.setAnchorView(R.id.nav_view)
            snackBar.show()
        } else {
            permissionLauncher.launch(permission)
        }
    }

    override fun onStop() {
        super.onStop()
        saveCurrentSong()
    }
    private fun setupComponents() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
           val windowMetrics = windowManager.currentWindowMetrics
           if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE){
               MusicAppUtils.DENSITY = windowMetrics.density
           }else{
               MusicAppUtils.DENSITY = 1f*windowMetrics.bounds.width()/ DisplayMetrics.DENSITY_DEFAULT
           }
        }else{
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            MusicAppUtils.DENSITY = displayMetrics.density

        }
        shareViewModel.initPlayList()
        sharedPreferences = getSharedPreferences(
            "net.braniumacademy.musicapplication.preff_file",
            MODE_PRIVATE
        )
    }
    private fun setupBottomNav() {
        val navView: BottomNavigationView = binding.navView
        val navHostFragment =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment)
        val navController = navHostFragment.navController
        navView.setupWithNavController(navController)
    }
    private fun saveCurrentSong() {
        val playingSong = sharedViewModel.playingSong.value
        playingSong?.let {
            val song = it.song
            song?.let { currentSong ->
                sharedPreferences.edit()
                    .putString(PREF_SONG_ID, currentSong.id)
                        .putString(PREF_PLAYLIST_NAME, it.playlist?.name).apply()

            }
        }
    }


    private fun loadPreviousSessionSong() {
        val songId = sharedPreferences.getString(PREF_SONG_ID, null)
        val playlistName = sharedPreferences.getString(PREF_PLAYLIST_NAME, null)
        sharedViewModel.loadPreviousSessionSong(songId, playlistName)




    }


    companion object {
        const val PREF_SONG_ID = "PREF_SONG_ID"
        const val PREF_PLAYLIST_NAME = "PREF_PLAYLIST_NAME"
    }

}