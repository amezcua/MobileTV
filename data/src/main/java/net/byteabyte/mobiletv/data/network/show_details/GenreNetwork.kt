package net.byteabyte.mobiletv.data.network.show_details

import net.byteabyte.mobiletv.core.tvshows.details.Genre

data class GenreNetwork(
    val id: Int,
    val name: String
) {
    fun toGenre() = Genre(
        id = id,
        name = name
    )
}