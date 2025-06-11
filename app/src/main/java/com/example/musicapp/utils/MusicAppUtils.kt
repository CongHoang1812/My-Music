package com.example.musicapp.utils

object MusicAppUtils {
    const val EXTRA_CURRENT_FRACTION = "EXTRA_CURRENT_FRACTION"
    const val DEFAULT_MARGIN_END = 48

    enum class DefaultPlaylistName(val value: String) {
        DEFAULT("Default"),
        FAVORITES("Favorites"),
        RECOMMENDED("Recommended"),
        RECENT("Recent"),
        SEARCH("Search"),
        MOST_HEARD("Most_Heard"),
        FOR_YOU("For_You"),
        CUSTOM("Custom")
    }
}