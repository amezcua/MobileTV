package net.byteabyte.mobiletv.data.network.retrofit.show_details

import com.squareup.moshi.Json
import net.byteabyte.mobiletv.data.network.show_details.GenreNetwork

data class JsonGenre(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String
) {
    fun toGenreNetwork() = GenreNetwork(id, name)
}